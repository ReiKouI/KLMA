package com.reikoui.klma.dao;

import com.reikoui.klma.domain.Word;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WordMapper {

    /**
     * 插入多条记录
     * @param words: 从CSV文件中获取的单词记录列表
     * @return 最后一条被插入数据的ID
     */
    int insertWordsList(List<Word> words);

    /**
     * 获取该用户所有的单词
     * @return
     */
    @Select("select * from word")
    List<Word> listWords();

    /**
     * 根据单词名称删除单词记录
     * @param wordName
     * @return
     */
    @Delete("delete from word where word = #{word}")
    int deleteByWordName(@Param("word") String wordName);

    /**
     * 插入单条记录
     * @param word
     * @return
     */
    @Insert("insert into word(word, chinese_meaning, example_sentence) values(#{word}, #{chineseMeaning}, #{exampleSentence})")
    int insert(Word word);

    /**
     * 获取指定数目的旧单词
     * @param personLexiconId 个人词库标识
     * @param num 数目
     * @return
     */
    @Select("select * from word where person_lexicon_id = #{personLexiconId} and coefficient > 0 and coefficient != 10 and last_date != #{date} order by coefficient desc limit #{num}")
    List<Word> listOldWords(@Param("personLexiconId")int personLexiconId, @Param("num") int num, @Param("date") String date);

    /**
     * 随机获取指定数目的新单词
     * @param personLexiconId 个人词库标识
     * @param num 数目
     * @return
     */
    @Select("select * from word where person_lexicon_id = #{personLexiconId} and coefficient = 0 order by rand() limit #{num}")
    List<Word> listNewWords(@Param("personLexiconId")int personLexiconId, @Param("num") int num);

}
