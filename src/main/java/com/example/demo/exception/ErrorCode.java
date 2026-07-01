package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    SUCCESS(1000, "Success", HttpStatus.OK),

    INVALID_INPUT(1001, "Du lieu khong hop le", HttpStatus.BAD_REQUEST),

    CUSTOMER_EXISTED(2001, "Khach hang da ton tai", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND(2002, "Khach hang khong ton tai", HttpStatus.NOT_FOUND),

    ACCOUNT_NOT_FOUND(3001, "Tai khoan khong ton tai", HttpStatus.NOT_FOUND),
    ACCOUNT_NUMBER_EXISTED(3002, "So tai khoan da ton tai", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_PENDING_APPROVAL(3003, "Tai khoan khong o trang thai cho phe duyet", HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT_STATUS(3004, "Trang thai tai khoan khong hop le", HttpStatus.BAD_REQUEST),

    INTERNAL_SERVER_ERROR(9999, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}