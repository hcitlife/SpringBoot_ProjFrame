package com.hc.comm.res;


/**
 * 消息处理工具类
 */
public class ResultUtil {
    /**
     * 操作成功，只返回结果码和提示信息
     *
     * @param resultCode
     * @return
     */
    public static Result success(ResultCode resultCode) {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 操作成功,只返回结果码和具体的数据，但不返回提示信息
     *
     * @return
     */
    public static Result success(int code, String msg) {
        return new Result(code, msg);
    }

    /**
     * 操作成功,返回具体的数据、结果码和提示信息
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result<Object> result = new Result(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 操作成功,返回具体的数据、结果码和提示信息
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result success(Integer code, String msg, Object data) {
        return new Result<>(code,msg,data);
    }


    /**
     * 操作成功，只返回结果码和提示信息
     *
     * @param resultCode
     * @return
     */
    public static Result fail(ResultCode resultCode) {
        return new Result(ResultCode.FAIL);
    }

    /**
     * 操作失败,只返回指定的结果码和具体的数据，但不返回提示信息
     *
     * @return
     */
    public static Result fail(int code, String msg) {
        return new Result(code, msg);
    }

    /**
     * 操作失败,返回具体的数据、结果码和提示信息
     *
     * @param data
     * @return
     */
    public static Result fail(Object data) {
        Result<Object> result = new Result(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 操作失败,返回具体的数据、结果码和提示信息
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result fail(Integer code, String msg, Object data) {
        return new Result<>(code,msg,data);
    }
}
