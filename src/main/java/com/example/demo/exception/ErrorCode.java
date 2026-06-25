package com.example.demo.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(1000,"Success"),
    CUSTOMER_EXISTED(1001,"Khach hang da ton tai"),
    CUSTOMER_NOT_FOUND(1002, "Khach hang khong ton tai"),
    INVALID_INPUT(1003, "Du lieu khong hop le"),
    INTERNAL_SERVER_ERROR(9999, "Internal server error");


    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
