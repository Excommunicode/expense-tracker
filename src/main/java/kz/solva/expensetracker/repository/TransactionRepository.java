package kz.solva.expensetracker.repository;

import kz.solva.expensetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t " +
            "FROM Transaction t " +
            "JOIN FETCH t.limit l " +
            "WHERE (t.accountFrom = :userId OR t.accountTo = :userId) " +
            "AND t.limitExceeded = true")
    List<Transaction> findAllByAccountFromOrAccountToAfterAndLimitExceeded(Long userId);


    @Query("SELECT SUM(t.sum) " +
            "FROM Transaction t " +
            "JOIN t.limit l " +
            "WHERE l.id = :limitId")
    Optional<BigDecimal> findTotalSumByLimitId(Long limitId);
}