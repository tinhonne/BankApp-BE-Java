package com.example.demo.service.impl;

import com.example.demo.dto.request.TransferRequest;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.TransactionResponse;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionStatus;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.TransactionMapping;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionMapping transactionMapping;

    private TransactionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TransactionServiceImpl(accountRepository, transactionRepository, transactionMapping);
    }

    @Test
    void nonexistentSourceDoesNotValidateDestinationOrCreateTransaction() {
        when(accountRepository.findByAccountNumber("1")).thenReturn(null);

        assertError(ErrorCode.SOURCE_ACCOUNT_NOT_FOUND, () -> service.transfer(request("1", "2", "10.00")));

        verify(accountRepository, never()).findByAccountNumber("2");
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void inactiveSourceDoesNotValidateDestinationOrCreateTransaction() {
        when(accountRepository.findByAccountNumber("1")).thenReturn(account("1", "20.00", 0));

        assertError(ErrorCode.SOURCE_ACCOUNT_INACTIVE, () -> service.transfer(request("1", "2", "10.00")));

        verify(accountRepository, never()).findByAccountNumber("2");
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void nonexistentDestinationDoesNotCreateTransaction() {
        when(accountRepository.findByAccountNumber("1")).thenReturn(account("1", "20.00", 1));
        when(accountRepository.findByAccountNumber("2")).thenReturn(null);

        assertError(ErrorCode.DESTINATION_ACCOUNT_NOT_FOUND, () -> service.transfer(request("1", "2", "10.00")));

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void inactiveDestinationDoesNotCreateTransaction() {
        when(accountRepository.findByAccountNumber("1")).thenReturn(account("1", "20.00", 1));
        when(accountRepository.findByAccountNumber("2")).thenReturn(account("2", "7.00", 0));

        assertError(ErrorCode.DESTINATION_ACCOUNT_INACTIVE, () -> service.transfer(request("1", "2", "10.00")));

        verify(accountRepository, never()).findByAccountNumberForUpdate(any());
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void sameAccountIsRejectedOnlyAfterBothValidationSteps() {
        Account account = account("1", "20.00", 1);
        when(accountRepository.findByAccountNumber("1")).thenReturn(account);

        assertError(ErrorCode.SAME_ACCOUNT_TRANSFER, () -> service.transfer(request("1", "1", "10.00")));

        verify(accountRepository, times(2)).findByAccountNumber("1");
        verify(accountRepository, never()).findByAccountNumberForUpdate(any());
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void insufficientBalanceSavesFailedTransactionWithoutSavingBalances() {
        Account source = account("1", "5.00", 1);
        Account destination = account("2", "7.00", 1);
        TransactionResponse expected = new TransactionResponse();
        mockLookupAndLocks(source, destination);
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionMapping.toResponse(any())).thenReturn(expected);

        TransactionResponse actual = service.transfer(request("1", "2", "10.00"));

        assertSame(expected, actual);
        assertEquals(new BigDecimal("5.00"), source.getBalance());
        assertEquals(new BigDecimal("7.00"), destination.getBalance());
        verify(transactionRepository).save(org.mockito.ArgumentMatchers.argThat(transaction ->
                transaction.getStatus() == TransactionStatus.INSUFFICIENT_BALANCE
                        && "Insufficient balance".equals(transaction.getErrorReason())));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void successfulTransferUpdatesBothBalancesAndReturnsMappedTransaction() {
        Account source = account("1", "20.00", 1);
        Account destination = account("2", "7.00", 1);
        TransactionResponse expected = new TransactionResponse();
        mockLookupAndLocks(source, destination);
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionMapping.toResponse(any())).thenReturn(expected);

        TransactionResponse actual = service.transfer(request("1", "2", "10.00"));

        assertSame(expected, actual);
        assertEquals(new BigDecimal("10.00"), source.getBalance());
        assertEquals(new BigDecimal("17.00"), destination.getBalance());
        verify(accountRepository).save(source);
        verify(accountRepository).save(destination);
        verify(transactionRepository).save(org.mockito.ArgumentMatchers.argThat(transaction ->
                transaction.getStatus() == TransactionStatus.SUCCESS
                        && transaction.getFromAccount() == source
                        && transaction.getToAccount() == destination
                        && transaction.getTransactionDate() != null));
    }

    @Test
    void historyMapsSentAndReceivedTransactionsFromRepositoryPage() {
        Transaction sent = transaction("1", "2", "10.00");
        Transaction received = transaction("3", "1", "4.00");
        TransactionResponse sentResponse = response("1", "2", "10.00");
        TransactionResponse receivedResponse = response("3", "1", "4.00");
        when(accountRepository.existsByAccountNumber("1")).thenReturn(true);
        when(transactionRepository.findHistory(org.mockito.ArgumentMatchers.eq("1"), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(sent, received)));
        when(transactionMapping.toResponse(sent)).thenReturn(sentResponse);
        when(transactionMapping.toResponse(received)).thenReturn(receivedResponse);

        PageResponse<TransactionResponse> result = service.getHistory("1", null, null, 0, 10);

        assertEquals(List.of(sentResponse, receivedResponse), result.getContent());
        verify(transactionMapping).toResponse(sent);
        verify(transactionMapping).toResponse(received);
    }

    @Test
    void historyPassesInclusiveDateBoundsPaginationAndDescendingOrdering() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2026, 1, 31, 23, 59, 59);
        when(accountRepository.existsByAccountNumber("1")).thenReturn(true);
        when(transactionRepository.findHistory(org.mockito.ArgumentMatchers.eq("1"), org.mockito.ArgumentMatchers.eq(from),
                org.mockito.ArgumentMatchers.eq(to), any())).thenReturn(new PageImpl<>(List.of()));

        service.getHistory("1", from, to, 2, 25);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(transactionRepository).findHistory(org.mockito.ArgumentMatchers.eq("1"),
                org.mockito.ArgumentMatchers.eq(from), org.mockito.ArgumentMatchers.eq(to), captor.capture());
        Pageable pageable = captor.getValue();
        assertEquals(2, pageable.getPageNumber());
        assertEquals(25, pageable.getPageSize());
        assertEquals(Sort.Direction.DESC, pageable.getSort().getOrderFor("transactionDate").getDirection());
        assertEquals(Sort.Direction.DESC, pageable.getSort().getOrderFor("id").getDirection());
    }

    @Test
    void invalidDateRangeDoesNotQueryHistory() {
        when(accountRepository.existsByAccountNumber("1")).thenReturn(true);
        LocalDateTime from = LocalDateTime.of(2026, 2, 1, 0, 0);
        LocalDateTime to = from.minusNanos(1);

        assertError(ErrorCode.INVALID_TRANSACTION_DATE_RANGE,
                () -> service.getHistory("1", from, to, 0, 10));

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void invalidPaginationDoesNotQueryHistory() {
        when(accountRepository.existsByAccountNumber("1")).thenReturn(true);

        assertError(ErrorCode.INVALID_PAGE_REQUEST, () -> service.getHistory("1", null, null, -1, 10));
        assertError(ErrorCode.INVALID_PAGE_REQUEST, () -> service.getHistory("1", null, null, 0, 0));
        assertError(ErrorCode.INVALID_PAGE_REQUEST, () -> service.getHistory("1", null, null, 0, 101));

        verifyNoInteractions(transactionRepository);
    }

    private void assertError(ErrorCode expected, Runnable invocation) {
        AppException exception = assertThrows(AppException.class, invocation::run);
        assertEquals(expected, exception.getErrorCode());
    }

    private void mockLookupAndLocks(Account source, Account destination) {
        when(accountRepository.findByAccountNumber(source.getAccountNumber())).thenReturn(source);
        when(accountRepository.findByAccountNumber(destination.getAccountNumber())).thenReturn(destination);
        when(accountRepository.findByAccountNumberForUpdate("1")).thenReturn(Optional.of(source));
        when(accountRepository.findByAccountNumberForUpdate("2")).thenReturn(Optional.of(destination));
    }

    private Account account(String number, String balance, int status) {
        Account account = new Account();
        account.setAccountNumber(number);
        account.setBalance(new BigDecimal(balance));
        account.setStatus(status);
        return account;
    }

    private Transaction transaction(String source, String destination, String amount) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(account(source, "0.00", 1));
        transaction.setToAccount(account(destination, "0.00", 1));
        transaction.setAmount(new BigDecimal(amount));
        return transaction;
    }

    private TransactionResponse response(String source, String destination, String amount) {
        TransactionResponse response = new TransactionResponse();
        response.setFromAccountNumber(source);
        response.setToAccountNumber(destination);
        response.setAmount(new BigDecimal(amount));
        return response;
    }

    private TransferRequest request(String source, String destination, String amount) {
        return new TransferRequest(source, destination, new BigDecimal(amount), "content");
    }
}
