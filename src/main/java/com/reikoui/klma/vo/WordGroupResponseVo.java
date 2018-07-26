package com.reikoui.klma.vo;

import com.reikoui.klma.domain.Word;
import lombok.Data;

import java.util.List;

@Data
public class WordGroupResponseVo {

    private Integer personLexiconId;
    private List<Word> newWords;
    private List<Word> oldWords;

}
