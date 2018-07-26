package com.reikoui.klma.utils;

import com.reikoui.klma.result.Result;
import com.reikoui.klma.result.CodeMessage;

public class ResultUtil {

    public static Result success(Object data) {
        return new Result(CodeMessage.SUCCESS, data);
    }

    public static Result error(CodeMessage codeMessage) {
        return new Result(codeMessage, null);
    }


}
