package kz.solva.expensetracker.repository;

import kz.solva.expensetracker.model.CurrencyPair;
import kz.solva.expensetracker.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExchangeRatesRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("SELECT e " +
            "FROM ExchangeRate e " +
            "WHERE e.currencyPair = :currencyPair " +
            "ORDER BY e.updatedAt DESC " +
            "LIMIT 1")
    Optional<ExchangeRate> findByUpdatedAtAndCurrencyPair(CurrencyPair currencyPair);
}