package com.reikoui.klma.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ReciteRecord {

    private String userId;
    private Date date;
    private int totalWords;
    private int newWords;

}
