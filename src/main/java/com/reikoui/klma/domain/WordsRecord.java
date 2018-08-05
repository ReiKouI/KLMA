package com.reikoui.klma.domain;

import lombok.Data;

import java.util.List;

@Data
public class WordsRecord {

    private Integer personLexiconId;
    private List<Word> words;

}
