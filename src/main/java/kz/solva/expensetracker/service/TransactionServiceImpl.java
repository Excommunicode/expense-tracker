package kz.solva.expensetracker.service;

import kz.solva.expensetracker.dto.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.dto.validate.LimitReferencesDto;
import kz.solva.expensetracker.mapper.LimitMapper;
import kz.solva.expensetracker.mapper.TransactionMapper;
import kz.solva.expensetracker.model.Limit;
import kz.solva.expensetracker.model.Transaction;
import kz.solva.expensetracker.repository.LimitRepository;
import kz.solva.expensetracker.repository.TransactionRepository;
import kz.solva.expensetracker.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static kz.solva.expensetracker.model.CurrencyCode.USD;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final LimitRepository limitRepository;
    private final LimitMapper limitMapper;



    @Override
    @Transactional
    public TransactionDto addTransaction(Transaction transaction) {
        log.info("Starting addTransaction with transaction: {}", transaction);

        Limit applicableLimit = resolveApplicableLimit(transaction);
        transaction.setLimit(applicableLimit);
        log.debug("Applicable limit resolved: {}", applicableLimit);

        boolean isLimitExceeded = checkLimitTransaction(transaction);
        transaction.setLimitExceeded(isLimitExceeded);
        log.info("Transaction limit exceeded check result: {}", isLimitExceeded);

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction saved: {}", savedTransaction);

        return transactionMapper.toDto(savedTransaction);
    }


    @Override
    public List<TransactionFullDto> findExceededTransaction(LimitReferencesDto limitReferencesDto) {

        List<Transaction> exceededTransactions = transactionRepository.findAllByLimitInAndLimitExceededOrderById(limitReferencesDto.getLimitsIds());

        return exceededTransactions.stream()
                .map(transaction -> {
                    TransactionFullDto dto = transactionMapper.toFullDto(transaction);
                    Limit limit = transaction.getLimit();
                    if (limit != null) {
                        dto.setLimitSum(limit.getLimitSum());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }



    private Limit resolveApplicableLimit(Transaction transaction) {
        if (Objects.isNull(transaction.getLimit())) {
            return createDefaultLimit(transaction.getAccountFrom());
        }

        Long limitId = transaction.getLimit().getId();
        if (!isLimitValid(limitId)) {
            return createDefaultLimit(transaction.getAccountFrom());
        }

        return transaction.getLimit();
    }

    private boolean isLimitValid(Long limitId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysLater = now.plusDays(30);
        return limitRepository.findByIdAndLimitDatetime(limitId, now, thirtyDaysLater);
    }

    private boolean checkLimitTransaction(Transaction transaction) {
        Limit limit = transaction.getLimit();

        Optional<BigDecimal> totalSumByLimitId = transactionRepository.findTotalSumByLimitId(limit.getId());
        return totalSumByLimitId
                .map(totalSum -> totalSum.add(transaction.getSum()))
                .map(updatedTotalSum -> updatedTotalSum.compareTo(limit.getLimitSum()) >= 0)
                .orElse(false);
    }

    private Limit createDefaultLimit(Long userId) {
        LocalDateTime afterThirtyDays = LocalDateTime.now().plusDays(30);
        BigDecimal defaultLimitSum = BigDecimal.valueOf(1000);

        Limit defualtLimit = limitMapper.createLimit(defaultLimitSum, afterThirtyDays, USD, userId);

        return limitRepository.save(defualtLimit);
    }




}