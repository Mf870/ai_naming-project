package com.example.ainaming.exception;

/**
 * 业务异常
 * 用于在 Service 层主动抛出可预期的业务错误（例如调用 DeepSeek 失败、
 * 返回内容解析失败等），由全局异常处理器统一捕获并转换为标准返回格式
 */
public class BusinessException extends RuntimeException {

    /** 错误码 */
    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public int getCode() {
        return code;
    }
}
