package com.hc.comm.ex;


import com.hc.comm.res.ResultCode;

import java.text.MessageFormat;

/**
 * 自定义异常类型
 * @author pyy
 **/
public class CustomException extends RuntimeException {

    //错误代码
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, Object... args){
        super(resultCode.getMsg());
        String message = MessageFormat.format(resultCode.getMsg(), args);
        resultCode.setMsg(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode(){
        return resultCode;
    }

}
