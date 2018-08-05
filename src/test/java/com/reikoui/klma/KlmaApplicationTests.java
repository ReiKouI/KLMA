package com.reikoui.klma;

import com.reikoui.klma.dao.PersonLexiconMapper;
import com.reikoui.klma.dao.ReciteRecordMapper;
import com.reikoui.klma.dao.WordMapper;
import com.reikoui.klma.domain.PersonLexicon;
import com.reikoui.klma.domain.ReciteRecord;
import com.reikoui.klma.domain.Word;
import com.reikoui.klma.domain.WordsRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KlmaApplicationTests {

//	@Autowired
//	WordMapper wordMapper;

	@Autowired
	ReciteRecordMapper reciteRecordMapper;

	@Test
	public void contextLoads() throws ParseException {
//		List<Word> words = new ArrayList<>();
//
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//
//		Word word1 = new Word();
//		word1.setWord("인도 사람");
//		word1.setCoefficient(0.4);
//		word1.setLastCoefficient(0.6);
//		Date date1 = simpleDateFormat.parse("2018-08-06");
//		word1.setLastDate(date1);
//		words.add(word1);
//
//		Word word2 = new Word();
//		word2.setWord("안녕하시니까");
//		word2.setCoefficient(0.4);
//		word2.setLastCoefficient(0.6);
//		Date date2 = simpleDateFormat.parse("2018-08-06");
//		word2.setLastDate(date2);
//		words.add(word2);
//
//		WordsRecord wordsRecord = new WordsRecord();
//		wordsRecord.setPersonLexiconId(1);
//		wordsRecord.setWords(words);
//
//		wordMapper.updateWordsListByWord(wordsRecord);

		ReciteRecord reciteRecord = new ReciteRecord();
		reciteRecord.setDate(simpleDateFormat.parse("2018-08-06"));
		reciteRecord.setUserId("12345678910");
		reciteRecord.setNewWords(2);
		reciteRecord.setTotalWords(10);
		reciteRecordMapper.update(reciteRecord);


	}

}
