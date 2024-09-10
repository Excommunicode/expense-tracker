package kz.solva.expensetracker.controller;

import jakarta.validation.Valid;
import kz.solva.expensetracker.dto.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.mapper.TransactionMapper;
import kz.solva.expensetracker.model.Transaction;
import kz.solva.expensetracker.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public TransactionDto createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        log.info("Endpoint /api/v1/transactions POST started. Received request to create transaction: {}", transactionDto);
        Transaction entity = transactionMapper.toEntity(transactionDto);
        return transactionService.addTransaction(entity);
    }

    @GetMapping("/{userId}")
    public List<TransactionFullDto> findExceededTransaction(@PathVariable Long userId) {
        log.info("Endpoint /api/v1/transactions/{userId} GET started. User ID: {}", userId);
        return transactionService.findExceededTransaction(userId);
    }

}
