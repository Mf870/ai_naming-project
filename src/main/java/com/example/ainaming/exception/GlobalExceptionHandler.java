package com.example.ainaming.exception;

import com.example.ainaming.dto.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一拦截 Controller 层抛出的各类异常，转换为前端约定的
 * { "code": ..., "msg": ..., "data": null } 格式返回，避免异常堆栈直接暴露给前端
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常（例如 DeepSeek 调用失败、结果解析失败等）
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK) // 业务异常统一以 200 状态码返回，由 code 字段区分成功/失败
    public ApiResult<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常（例如 @NotBlank 校验不通过）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败：{}", message);
        return ApiResult.error(400, message);
    }

    /**
     * 兜底处理所有未被捕获的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return ApiResult.error(500, "服务器内部错误，请稍后重试");
    }
}
