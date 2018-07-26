package com.reikoui.klma.service;

import com.reikoui.klma.dao.PersonLexiconMapper;
import com.reikoui.klma.dao.PlanMapper;
import com.reikoui.klma.dao.UserMapper;
import com.reikoui.klma.domain.PersonLexicon;
import com.reikoui.klma.domain.Plan;
import com.reikoui.klma.domain.User;
import com.reikoui.klma.exception.GlobalException;
import com.reikoui.klma.redis.RedisService;
import com.reikoui.klma.result.CodeMessage;
import com.reikoui.klma.utils.MD5Util;
import com.reikoui.klma.utils.UUIDUtil;
import com.reikoui.klma.vo.LoginRequestVo;
import com.reikoui.klma.vo.LoginResponseVo;
import com.reikoui.klma.vo.PlanVo;
import com.reikoui.klma.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PlanMapper planMapper;

    @Autowired
    PersonLexiconMapper personLexiconMapper;

    @Autowired
    RedisService redisService;

    public int signUp(RegisterVo registerVo) {
        String id = registerVo.getId();
        String nickname = registerVo.getNickname();
        String formMD5 = registerVo.getPassword();
        if (userMapper.selectById(id) != null) {
            throw new GlobalException(CodeMessage.ID_REPETITION);
        }
        // 生成盐
        String randomSalt = MD5Util.getRandomSalt();
        String password = MD5Util.formMD5toDBMD5(formMD5, randomSalt);
        User user = new User();
        user.setId(id);
        user.setNickname(nickname);
        user.setSalt(randomSalt);
        user.setPassword(password);
        return userMapper.insert(user);
    }


    public LoginResponseVo login(LoginRequestVo loginRequestVo, HttpServletResponse httpServletResponse) {
        if (loginRequestVo == null) {
            throw new GlobalException(CodeMessage.SERVER_ERROR);
        }
        String id = loginRequestVo.getId();
        String formMD5 = loginRequestVo.getPassword();
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new GlobalException(CodeMessage.USER_EMPTY);
        }
        String formMD5toDBMD5 = MD5Util.formMD5toDBMD5(formMD5, user.getSalt());
        String DBMD5 = user.getPassword();
        if (!DBMD5.equals(formMD5toDBMD5)) {
            throw new GlobalException(CodeMessage.PASSWD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(httpServletResponse, token, user);
        // 剔除隐私数据
        user.setPassword(null);
        user.setSalt(null);
        // 获取词库列表
        List<PersonLexicon> personLexicons = personLexiconMapper.listLexiconsByUserId(user.getId());

        LoginResponseVo loginResponseVo = new LoginResponseVo();
        // 获取当前学习词库
        if (user.getCurrentLexiconId() != null) {
            PersonLexicon personLexicon = personLexiconMapper.selectPersonLexiconById(user.getCurrentLexiconId());
            loginResponseVo.setCurrentLexicon(personLexicon);
        }
        // 获取当前计划
        if (user.getPlanId() != null) {
            Plan plan = planMapper.selectPlanById(user.getPlanId());
            loginResponseVo.setCurrentPlan(plan);
        }
        loginResponseVo.setUser(user);
        loginResponseVo.setLexiconVoList(personLexicons);
        return loginResponseVo;
    }

    @Transactional
    public int setPlan(User user, PlanVo planVo) {
        Plan plan = new Plan();
        plan.setNewWords(planVo.getNewWords());
        plan.setTotalWords(planVo.getTotalWords());
        planMapper.insert(plan);
        int planId = plan.getId();
        user.setPlanId(planId);
        userMapper.updatePlan(user);
        return planId;
    }

    public int getPlanId(String userId) {
        return userMapper.selectPlanIdById(userId);
    }

    public int setLexicon(User user) {
        return userMapper.updateLexicon(user);
    }

    public User getByToken(HttpServletResponse httpServletResponse, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get("User:", token, User.class);
        if (user != null) {
            addCookie(httpServletResponse, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set("User:", 600, token, user);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(600);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
