package com.reikoui.klma.service;

import com.reikoui.klma.dao.PersonLexiconMapper;
import com.reikoui.klma.dao.WordMapper;
import com.reikoui.klma.domain.PersonLexicon;
import com.reikoui.klma.domain.Word;
import com.reikoui.klma.exception.GlobalException;
import com.reikoui.klma.result.CodeMessage;
import com.reikoui.klma.result.Result;
import com.reikoui.klma.utils.FileUtil;
import com.reikoui.klma.utils.ResultUtil;
import com.sun.tools.javac.jvm.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class WordService {

    @Autowired
    WordMapper wordMapper;

    @Autowired
    PersonLexiconMapper personLexiconMapper;

    @Transactional
    public Result uploadCsv(MultipartFile file,
                            Integer categoryId,
                            String lexiconName,
                            String userId
    ) {
        int count = 0;
        try {
            PersonLexicon personLexicon = new PersonLexicon();
            personLexicon.setCategoryId(categoryId);
            personLexicon.setName(lexiconName);
            personLexicon.setUserId(userId);
            personLexiconMapper.insert(personLexicon);
            List<Word> words = FileUtil.csv2List(file.getInputStream(), personLexicon.getId());
            count = wordMapper.insertWordsList(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(count);
    }


}
