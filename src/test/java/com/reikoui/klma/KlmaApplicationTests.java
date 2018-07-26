package com.reikoui.klma;

import com.reikoui.klma.dao.PersonLexiconMapper;
import com.reikoui.klma.domain.PersonLexicon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KlmaApplicationTests {

	@Autowired
	PersonLexiconMapper personLexiconMapper;

	@Test
	public void contextLoads() {
		PersonLexicon personLexicon = new PersonLexicon();
		personLexicon.setCategoryId(3);
		personLexicon.setName("初级韩语");
		System.out.println(personLexiconMapper.insert(personLexicon));
	}

}
