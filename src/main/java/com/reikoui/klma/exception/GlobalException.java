package com.reikoui.klma.exception;

import com.reikoui.klma.result.CodeMessage;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMessage codeMessage;

    public GlobalException(CodeMessage codeMessage) {
        super(codeMessage.toString());
        this.codeMessage = codeMessage;
    }

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }
}
