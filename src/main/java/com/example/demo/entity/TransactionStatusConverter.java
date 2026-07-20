package com.example.demo.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TransactionStatus status) {
        return status == null ? null : status.getCode();
    }

    @Override
    public TransactionStatus convertToEntityAttribute(Integer code) {
        return TransactionStatus.fromCode(code);
    }
}
