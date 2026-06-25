package com.example.demo.dto.response;

import com.example.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{

    private int code;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success(T result){
        return ApiResponse.<T>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(result)
                .build();
    }
    public static ApiResponse<?> error(ErrorCode errorCode){
        return ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
    public static ApiResponse<?> error(ErrorCode errorCode, String message){
        return ApiResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();
    }
}
