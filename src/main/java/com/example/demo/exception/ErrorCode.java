package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    SUCCESS(1000, "Success", HttpStatus.OK),

    INVALID_INPUT(1001, "Du lieu khong hop le", HttpStatus.BAD_REQUEST),

    CUSTOMER_EXISTED(2001, "Khach hang da ton tai", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND(2002, "Khach hang khong ton tai", HttpStatus.NOT_FOUND),
    CUSTOMER_HAS_ACCOUNT(2003, "Khach hang con tai khoan dang hoat dong, khong the xoa", HttpStatus.BAD_REQUEST),

    ACCOUNT_NOT_FOUND(3001, "Tai khoan khong ton tai", HttpStatus.NOT_FOUND),
    ACCOUNT_NUMBER_EXISTED(3002, "So tai khoan da ton tai", HttpStatus.CONFLICT),
    ACCOUNT_NOT_PENDING_APPROVAL(3003, "Tai khoan khong o trang thai cho phe duyet", HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT_STATUS(3004, "Trang thai tai khoan khong hop le", HttpStatus.BAD_REQUEST),

    INTERNAL_SERVER_ERROR(9999, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(4001,"tai khoan dang nhap da ton tai",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4002,"tai khoan khong ton tai",HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(5001,"dang nhap khong thanh conh",HttpStatus.BAD_REQUEST),

    SOURCE_ACCOUNT_NOT_FOUND(6001, "Tai khoan nguon khong ton tai", HttpStatus.NOT_FOUND),
    SOURCE_ACCOUNT_INACTIVE(6002, "Tai khoan nguon khong hoat dong", HttpStatus.BAD_REQUEST),
    DESTINATION_ACCOUNT_NOT_FOUND(6003, "Tai khoan dich khong ton tai", HttpStatus.NOT_FOUND),
    DESTINATION_ACCOUNT_INACTIVE(6004, "Tai khoan dich khong hoat dong", HttpStatus.BAD_REQUEST),
    SAME_ACCOUNT_TRANSFER(6005, "Tai khoan nguon va dich phai khac nhau", HttpStatus.BAD_REQUEST),
    INVALID_TRANSFER_AMOUNT(6006, "So tien chuyen toi thieu la 0.01", HttpStatus.BAD_REQUEST),
    INVALID_TRANSACTION_DATE_RANGE(6007, "Khoang thoi gian giao dich khong hop le", HttpStatus.BAD_REQUEST),
    INVALID_PAGE_REQUEST(6008, "Thong tin phan trang khong hop le", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}