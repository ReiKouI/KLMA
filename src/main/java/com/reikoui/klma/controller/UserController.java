package com.reikoui.klma.controller;

import com.reikoui.klma.domain.User;
import com.reikoui.klma.result.CodeMessage;
import com.reikoui.klma.result.Result;
import com.reikoui.klma.service.UserService;
import com.reikoui.klma.utils.ResultUtil;
import com.reikoui.klma.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result login(@RequestBody @Valid LoginRequestVo loginRequestVo, HttpServletResponse httpServletResponse) {
        logger.info("{}", loginRequestVo.toString());
        LoginResponseVo login = userService.login(loginRequestVo, httpServletResponse);
        return ResultUtil.success(login);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result signUp(@RequestBody @Valid RegisterVo registerVo) {
        logger.info("{}", registerVo.toString());
        int result = userService.signUp(registerVo);
        return ResultUtil.success(result);
    }

    @RequestMapping(value = "/plan", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result setPlan(@CookieValue(value = "token", required = false) String cookieToken,
                       @RequestParam(value = "token", required = false) String paramToken,
                          @RequestBody PlanVo planVo,
                          HttpServletResponse httpServletResponse) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(httpServletResponse, token);
        int planId = userService.setPlan(user, planVo);
        return ResultUtil.success(planId);
    }

    @RequestMapping(value = "/plan", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result getPlan(@CookieValue(value = "token", required = false) String cookieToken,
                          @RequestParam(value = "token", required = false) String paramToken,
                          HttpServletResponse httpServletResponse
    ) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(httpServletResponse, token);
        // 可优化
        int planId = userService.getPlanId(user.getId());
        Entry<String, Integer> entry = new Entry<String, Integer>("planId", planId);
        return ResultUtil.success(entry);
    }


    @RequestMapping(value = "/lexicon", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result setLexicon(@CookieValue(value = "token", required = false) String cookieToken,
                          @RequestParam(value = "token", required = false) String paramToken,
                          @RequestBody LexiconVo lexiconVo,
                          HttpServletResponse httpServletResponse) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        String userId = userService.getByToken(httpServletResponse, token).getId();
        User user = new User();
        user.setId(userId);
        user.setCurrentLexiconId(lexiconVo.getCurrentLexiconId());
        int modifyRows = userService.setLexicon(user);
        if (modifyRows == 1) {
            return ResultUtil.success(true);
        }
        return ResultUtil.success(false);
    }

    @RequestMapping(value = "/lexicon", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public Result getLexiconId(@CookieValue(value = "token", required = false) String cookieToken,
                          @RequestParam(value = "token", required = false) String paramToken,
                          HttpServletResponse httpServletResponse
    ) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return ResultUtil.error(CodeMessage.NOT_LOGIN);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(httpServletResponse, token);
        // 可优化
        int planId = userService.getPlanId(user.getId());
        Entry<String, Integer> entry = new Entry<String, Integer>("planId", planId);
        return ResultUtil.success(entry);
    }


}
