package com.hc.comm.ex;

import com.hc.comm.res.Result;
import com.hc.comm.res.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public Result handleException(CustomException e) {
        // 打印异常信息
        log.error("### 异常信息:{} ###", e.getMessage());
        return new Result(e.getResultCode());
    }


    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleOtherException(Exception e){
        //打印异常堆栈信息
        e.printStackTrace();
        // 打印异常信息
        log.error("### 不可知的异常:{} ###", e.getMessage());
        return new Result(ResultCode.SYSTEM_INNER_ERROR);
    }

}