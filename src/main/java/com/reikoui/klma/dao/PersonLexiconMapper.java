package com.reikoui.klma.dao;

import com.reikoui.klma.domain.PersonLexicon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PersonLexiconMapper {
    /**
     * 插入二级类目名称
     * @param personLexicon
     * @return
     */
    int insert(PersonLexicon personLexicon);

    @Select("select * from person_lexicon where user_id = #{userId}")
    List<PersonLexicon> listLexiconsByUserId(@Param("userId") String userId);

    @Select("select * from person_lexicon where id = #{id}")
    PersonLexicon selectPersonLexiconById(@Param("id") int id);


}
