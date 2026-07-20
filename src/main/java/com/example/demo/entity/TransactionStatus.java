package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    SUCCESS(0),
    INSUFFICIENT_BALANCE(3),
    SYSTEM_ERROR(4);

    private final int code;

    TransactionStatus(int code) {
        this.code = code;
    }

    public static TransactionStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (TransactionStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown transaction status code: " + code);
    }
}
