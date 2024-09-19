package kz.solva.expensetracker.service.api;

import kz.solva.expensetracker.dto.LimitReferencesDto;
import kz.solva.expensetracker.dto.validate.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.model.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionDto addTransaction(Transaction transaction);

    List<TransactionFullDto> findExceededTransaction(LimitReferencesDto limitReferencesDto);

}