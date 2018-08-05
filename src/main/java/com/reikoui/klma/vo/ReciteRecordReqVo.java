package com.reikoui.klma.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ReciteRecordReqVo {

    private String userId;
    private Date date;
    private int newIncrement;
    private int TotalIncrement;


}
