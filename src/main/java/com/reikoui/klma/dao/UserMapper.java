package com.reikoui.klma.dao;

import com.reikoui.klma.domain.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("insert into user(id, password, nickname, salt) values(#{id}, #{password}, #{nickname}, #{salt})")
    public int insert(User user);

    @Update("update user set plan_id = #{planId} where id = #{id}")
    public int updatePlan(User user);

    @Update("update user set current_lexicon_id = #{currentLexiconId} where id = #{id}")
    public int updateLexicon(User user);

    @Select("select * from user where id = #{id}")
    public User selectById(@Param("id") String id);

    @Select("select plan_id from user where id = #{id}")
    public int selectPlanIdById(@Param("id") String id);

}
