package com.reikoui.klma.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Word {
    private String word;
    private Integer personLexiconId;
    private String chineseMeaning;
    private String soundUrl;
    private String soundmark;
    private String exampleSentence;
//    private Integer learnTimes;
    private Double coefficient;
    private Double lastCoefficient;
    private Date lastDate;
}
