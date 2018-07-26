package com.reikoui.klma.utils;

import com.reikoui.klma.domain.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static List<Word> csv2List(InputStream inputStream, int id) {
        LinkedList<Word> words = new LinkedList<>();
        try(
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                Word word = new Word();
                word.setWord(split[0]);
                word.setChineseMeaning(split[1]);
                word.setExampleSentence(split[2]);
                word.setPersonLexiconId(id);
                words.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

}
