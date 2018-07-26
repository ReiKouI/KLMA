package com.reikoui.klma.vo;

import com.reikoui.klma.domain.PersonLexicon;
import com.reikoui.klma.domain.Plan;
import com.reikoui.klma.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponseVo {

    private User user;
    private List<PersonLexicon> lexiconVoList;
    private Plan currentPlan;
    private PersonLexicon currentLexicon;

}
