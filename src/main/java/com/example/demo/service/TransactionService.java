package com.example.demo.service;

import com.example.demo.dto.request.TransferRequest;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.TransactionResponse;

import java.time.LocalDateTime;

public interface TransactionService {
    TransactionResponse transfer(TransferRequest request);
    PageResponse<TransactionResponse> getHistory(String accountNumber, LocalDateTime fromDate, LocalDateTime toDate, int page, int size);
}
