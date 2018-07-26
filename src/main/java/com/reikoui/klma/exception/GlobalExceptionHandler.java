package com.reikoui.klma.exception;

import com.reikoui.klma.result.Result;
import com.reikoui.klma.result.CodeMessage;
import com.reikoui.klma.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return ResultUtil.error(globalException.getCodeMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            ObjectError error = bindingResult.getAllErrors().get(0);
            String defaultMessage = error.getDefaultMessage();
            return ResultUtil.error(CodeMessage.BIND_ERROR.fillMessage(defaultMessage));
        } else {
            e.printStackTrace();
            logger.info("exceptionHandler: Exception");
            return ResultUtil.error(CodeMessage.SERVER_ERROR);
        }
    }

}
