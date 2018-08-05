package com.reikoui.klma.vo;

import com.reikoui.klma.domain.Word;
import lombok.Data;

import java.util.List;

@Data
public class WordsRecordReqVo {
    private Integer personLexiconId;
    private List<Word> words;
}
