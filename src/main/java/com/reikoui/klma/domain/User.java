package com.reikoui.klma.domain;

import lombok.Data;

@Data
public class User {

    private String id;
    private String password;
    private String nickname;
    private String salt;
    private Integer planId;
    private Integer currentLexiconId;

}
