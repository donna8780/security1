package com.security.basic.domain.dto.resp;

public record ResultResponseDto<T>(
        boolean success,
        String code,
        String message,
        T data
) {
    public static final String CODE_SUCCEED = "SUCCEED";
    public static final String CODE_FAILED = "FAILED";

    public static <T> ResultResponseDto<T> ok (T data) {
        return new ResultResponseDto<>(true, CODE_SUCCEED, null, data);
    }

    public static <T> ResultResponseDto<T> fail (String message) {
        return new ResultResponseDto<>(false, CODE_FAILED, message, null);
    }
}
