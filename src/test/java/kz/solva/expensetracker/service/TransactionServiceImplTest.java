package kz.solva.expensetracker.service;

import kz.solva.expensetracker.dto.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.mapper.TransactionMapper;
import kz.solva.expensetracker.model.ExpenseCategory;
import kz.solva.expensetracker.model.Limit;
import kz.solva.expensetracker.model.Transaction;
import kz.solva.expensetracker.model.User;
import kz.solva.expensetracker.repository.LimitRepository;
import kz.solva.expensetracker.repository.TransactionRepository;
import kz.solva.expensetracker.repository.UserRepository;
import kz.solva.expensetracker.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static kz.solva.expensetracker.model.CurrencyCode.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TransactionServiceImplTest {
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final LimitRepository limitRepository;
    private final UserRepository userRepository;

    private Transaction transaction;
    private User accountFrom;
    private User acccountTo;
    private Limit limit;

    @BeforeEach
    void setUp() {
        accountFrom = Instancio.of(User.class).ignore(Select.field(User::getId)).create();
        acccountTo = Instancio.of(User.class).ignore(Select.field(User::getId)).create();
        userRepository.save(accountFrom);
        userRepository.save(acccountTo);
        limit = Instancio.of(Limit.class)
                .ignore(Select.field(Limit::getId))
                .set(Select.field(Limit::getUser), accountFrom)
                .create();

        transaction = Instancio.of(Transaction.class).ignore(Select.field(Transaction::getId))
                .ignore(Select.field(Transaction::getLimit))
                .ignore(Select.field(Transaction::getLimitExceeded))
                .set(Select.field(Transaction::getCurrencyShortname), USD)
                .set(Select.field(Transaction::getExpenseCategory), ExpenseCategory.GOODS)
                .set(Select.field(Transaction::getAccountFrom), accountFrom)
                .set(Select.field(Transaction::getAccountTo), acccountTo)
                .create();


        limitRepository.save(limit);


    }

    @Test
    void addTransaction_WithEmptyLimit() {
        TransactionDto savedDto = transactionService.addTransaction(transaction);

        Transaction savedTransaction = transactionRepository.findById(savedDto.getId()).orElse(null);

        Transaction expectedTransaction = transactionMapper.toEntity(savedDto);

        assertEquals(expectedTransaction.getId(), savedTransaction.getId());
        assertEquals(expectedTransaction.getCurrencyShortname(), savedTransaction.getCurrencyShortname());
        assertEquals(expectedTransaction.getSum(), savedTransaction.getSum());
        assertEquals(expectedTransaction.getExpenseCategory(), savedTransaction.getExpenseCategory());
        assertEquals(expectedTransaction.getDatetime(), savedTransaction.getDatetime());

    }

    @Test
    void findExceededTransactionTest() {
        List<Transaction> transactions = Stream.generate(() -> Instancio.of(Transaction.class)
                        .ignore(Select.field(Transaction::getId))
                        .set(Select.field(Transaction::getLimitExceeded), true)
                        .set(Select.field(Transaction::getLimit), limit)
                        .set(Select.field(Transaction::getAccountFrom), accountFrom)
                        .set(Select.field(Transaction::getAccountTo), acccountTo)
                        .create())
                .limit(10)
                .toList();

        List<Transaction> savedAll = transactionRepository.saveAll(transactions);

        List<TransactionFullDto> exceededTransaction = transactionService.findExceededTransaction(accountFrom.getId());
        exceededTransaction.sort(Comparator.comparing(TransactionFullDto::getId).reversed());

        assertNotNull(exceededTransaction);
        assertEquals(transactions.size(), exceededTransaction.size());

        for (TransactionFullDto transactionFullDto : exceededTransaction) {
            assertTrue(transactionFullDto.getLimitExceeded());
        }

    }


}
