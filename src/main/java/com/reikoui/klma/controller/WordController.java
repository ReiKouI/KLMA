package com.reikoui.klma.controller;

import com.reikoui.klma.dao.ReciteRecordMapper;
import com.reikoui.klma.dao.WordMapper;
import com.reikoui.klma.domain.*;
import com.reikoui.klma.exception.GlobalException;
import com.reikoui.klma.result.CodeMessage;
import com.reikoui.klma.result.Result;
import com.reikoui.klma.service.CategoryService;
import com.reikoui.klma.service.UserService;
import com.reikoui.klma.service.WordService;
import com.reikoui.klma.utils.ResultUtil;
import com.reikoui.klma.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/word")
public class WordController {

    Logger logger = LoggerFactory.getLogger(WordController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    WordService wordService;

    @Autowired
    UserService userService;

    @Autowired
    WordMapper wordMapper;

    @Autowired
    ReciteRecordMapper reciteRecordMapper;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result uploadCsv(@RequestParam("file") MultipartFile file,
                            @RequestParam("categoryId") Integer categoryId,
                            @RequestParam("lexiconName") String lexiconName,
                            @CookieValue(value = "token", required = false) String cookieToken,
                            @RequestParam(value = "token", required = false) String paramToken,
                            HttpServletResponse httpServletResponse
    ) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(httpServletResponse, token);
        if (user == null) {
            throw new GlobalException(CodeMessage.NOT_LOGIN);
        }
        if (file == null) {
            throw new GlobalException(CodeMessage.FILE_EMPTY);
        }
        String userId = user.getId();
        if (categoryId == null || lexiconName == null || lexiconName.equals("") || userId == null || userId.equals("")) {
            throw new GlobalException(CodeMessage.PARA_INCOMPLETE);
        }
        logger.info("categoryId: " + categoryId);
        logger.info("lexiconName: " + lexiconName);
        logger.info("userId: " + user.getId());
        return wordService.uploadCsv(file, categoryId, lexiconName, userId);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public List<Category> listCategories(
    ) {
        return categoryService.listCategories();
    }



    @RequestMapping(value = "/group", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result getGroupWords(@RequestBody WordGroupRequestVo wordGroupRequestVo) {
        // 8个新单词 2个旧单词
        Integer newNum = wordGroupRequestVo.getNewNum();
        logger.info("newNum: {}", newNum);
        Integer oldNum = wordGroupRequestVo.getOldNum();
        logger.info("oldNum: {}", oldNum);
        Integer personLexiconId = wordGroupRequestVo.getPersonLexiconId();
        logger.info("personLexiconId: {}", personLexiconId);

        // 获取当前日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        logger.info("date: {}", date);

        List<Word> oldWords = wordMapper.listOldWords(personLexiconId, oldNum, date);
        Integer diff = oldNum - oldWords.size();
        List<Word> newWords = wordMapper.listNewWords(personLexiconId, newNum + diff);
        WordGroupResponseVo wordGroupResponseVo = new WordGroupResponseVo();
        wordGroupResponseVo.setPersonLexiconId(personLexiconId);
        wordGroupResponseVo.setNewWords(newWords);
        wordGroupResponseVo.setOldWords(oldWords);
        logger.info(wordGroupRequestVo.getOldNum().toString());
        logger.info(wordGroupRequestVo.getNewNum().toString());
        return ResultUtil.success(wordGroupResponseVo);
    }

    @RequestMapping(value = "/record", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result updateWordsRecord(
            HttpServletResponse httpServletResponse,
            @CookieValue(value = "token", required = false) String cookieToken,
            @RequestParam(value = "token", required = false) String paramToken,
            @RequestBody WordsRecordReqVo wordsRecordReqVo) {
        List<Word> words = wordsRecordReqVo.getWords();
        logger.info("words: {}", words);
        Integer personLexiconId = wordsRecordReqVo.getPersonLexiconId();
        logger.info("personLexiconId: {}", personLexiconId);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(httpServletResponse, token);
        if (user == null) {
            throw new GlobalException(CodeMessage.NOT_LOGIN);
        }
        WordsRecord wordsRecord = new WordsRecord();
        wordsRecord.setWords(wordsRecordReqVo.getWords());
        wordsRecord.setPersonLexiconId(wordsRecordReqVo.getPersonLexiconId());
        wordMapper.updateWordsListByWord(wordsRecord);
        Entry<String, Boolean> resultEntry = new Entry<String, Boolean>("updateWordsRecord", true);
        return ResultUtil.success(resultEntry);
    }

    @RequestMapping(value = "/recite", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result addReciteRecord(
            HttpServletResponse httpServletResponse,
            @CookieValue(value = "token", required = false) String cookieToken,
            @RequestParam(value = "token", required = false) String paramToken
    ) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(httpServletResponse, token);
        if (user == null) {
            throw new GlobalException(CodeMessage.NOT_LOGIN);
        }
        ReciteRecord reciteRecord = new ReciteRecord();
        reciteRecord.setUserId(user.getId());
        reciteRecord.setDate(new Date());
        reciteRecord.setTotalWords(0);
        reciteRecord.setNewWords(0);
        reciteRecordMapper.insert(reciteRecord);
        Entry<String, Boolean> result = new Entry<>("addReciteRecord", true);
        return ResultUtil.success(result);
    }

    @RequestMapping(value = "/recite", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result updateReciteRecord(
            HttpServletResponse httpServletResponse,
            ReciteRecordReqVo reciteRecordReqVo,
            @CookieValue(value = "token", required = false) String cookieToken,
            @RequestParam(value = "token", required = false) String paramToken
    ) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(httpServletResponse, token);
        if (user == null) {
            throw new GlobalException(CodeMessage.NOT_LOGIN);
        }
        ReciteRecord reciteRecord = reciteRecordMapper.selectByUserIdAndDate(user.getId(), new Date());
        reciteRecord.setTotalWords(reciteRecord.getTotalWords() + reciteRecordReqVo.getTotalIncrement());
        reciteRecord.setNewWords(reciteRecord.getNewWords() + reciteRecordReqVo.getNewIncrement());
        reciteRecordMapper.update(reciteRecord);
        Entry<String, Boolean> result = new Entry<>("updateReciteRecord", true);
        return ResultUtil.success(result);
    }





}
