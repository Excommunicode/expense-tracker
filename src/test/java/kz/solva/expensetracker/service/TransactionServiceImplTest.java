package kz.solva.expensetracker.service;

import kz.solva.expensetracker.base.BaseTest;
import kz.solva.expensetracker.dto.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.dto.LimitReferencesDto;
import kz.solva.expensetracker.mapper.TransactionMapper;
import kz.solva.expensetracker.model.ExpenseCategory;
import kz.solva.expensetracker.model.Limit;
import kz.solva.expensetracker.model.Transaction;
import kz.solva.expensetracker.repository.LimitRepository;
import kz.solva.expensetracker.repository.TransactionRepository;
import kz.solva.expensetracker.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static kz.solva.expensetracker.model.CurrencyCode.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TransactionServiceImplTest extends BaseTest {
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final LimitRepository limitRepository;


    private Transaction transaction;

    private Limit limit;

    @BeforeEach
    void setUp() {

        limit = Instancio.of(Limit.class)
                .ignore(Select.field(Limit::getId))
                .create();

        transaction = Instancio.of(Transaction.class)
                .ignore(Select.field(Transaction::getId))
                .set(Select.field(Transaction::getLimit), limit)
                .ignore(Select.field(Transaction::getLimitExceeded))
                .set(Select.field(Transaction::getCurrencyShortname), USD)
                .set(Select.field(Transaction::getExpenseCategory), ExpenseCategory.GOODS)

                .create();

        limitRepository.save(limit);
    }


    @Test
    void addTransaction_WithEmptyLimit() {
        TransactionDto savedDto = transactionService.addTransaction(transaction);

        Transaction savedTransaction = transactionRepository.findById(savedDto.getId()).orElse(null);
        TransactionDto transactionDto = transactionMapper.toDto(savedTransaction);


        assertEquals(savedDto.getId(), transactionDto.getId());
        assertEquals(savedDto.getCurrencyShortname(), transactionDto.getCurrencyShortname());
        assertEquals(savedDto.getSum(), transactionDto.getSum());
        assertEquals(savedDto.getExpenseCategory(), transactionDto.getExpenseCategory());
        assertEquals(savedDto.getDatetime(), transactionDto.getDatetime());

    }

    @Test
    void findExceededTransactionTest() {
        List<Limit> limits = Stream.generate(() -> Instancio.of(Limit.class)
                        .ignore(Select.field(Limit::getId))
                        .create())
                .limit(10)
                .toList();

        List<Transaction> transactions = IntStream.range(0, 10)
                .mapToObj(i -> Instancio.of(Transaction.class)
                        .ignore(Select.field(Transaction::getId))
                        .set(Select.field(Transaction::getLimitExceeded), true)
                        .set(Select.field(Transaction::getLimit), limits.get(i))
                        .create())
                .toList();

        List<Limit> limits1 = limitRepository.saveAll(limits);
        transactionRepository.saveAll(transactions);


        List<Long> listIds = limits1.stream()
                .map(Limit::getId)
                .toList();

        LimitReferencesDto limitReferencesDto = LimitReferencesDto.builder()
                .limitsIds(listIds)
                .build();

        List<TransactionFullDto> exceededTransaction = transactionService.findExceededTransaction(limitReferencesDto);

        assertNotNull(exceededTransaction);

        assertEquals(exceededTransaction.size(), transactions.size());

        for (TransactionFullDto transactionFullDto : exceededTransaction) {
            assertTrue(transactionFullDto.getLimitExceeded());
        }
    }


}
