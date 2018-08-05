package com.reikoui.klma.dao;

import com.reikoui.klma.domain.ReciteRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface ReciteRecordMapper {

    @Insert("insert into recite_record(user_id, date, total_words, new_words) values(#{userId}, #{date}, #{totalWords}, #{newWords})")
    int insert(ReciteRecord reciteRecord);

    @Update("update recite_record set total_words = #{totalWords} , new_words = #{newWords} where user_id = #{userId} and date = #{date}")
    void update(ReciteRecord reciteRecord);

    @Select("select * from recite_record where user_id = #{userId} and date = #{date}")
    ReciteRecord selectByUserIdAndDate(@Param("userId") String userId, @Param("date") Date date);

}
