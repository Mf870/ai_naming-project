package com.example.ainaming.dto;

/**
 * 统一 REST 返回结果封装
 * 格式：{ "code": 200, "msg": "success", "data": ... }
 *
 * @param <T> data 部分的数据类型
 */
public class ApiResult<T> {

    /** 状态码，200 表示成功，其他表示失败 */
    private int code;

    /** 提示信息 */
    private String msg;

    /** 业务数据 */
    private T data;

    public ApiResult() {
    }

    public ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 构造成功返回结果 */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(200, "success", data);
    }

    /** 构造失败返回结果 */
    public static <T> ApiResult<T> error(int code, String msg) {
        return new ApiResult<>(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
