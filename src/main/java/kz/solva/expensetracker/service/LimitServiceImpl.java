package kz.solva.expensetracker.service;

import kz.solva.expensetracker.dto.LimitDto;
import kz.solva.expensetracker.exception.BadRequestException;
import kz.solva.expensetracker.exception.NotFoundException;
import kz.solva.expensetracker.mapper.LimitMapper;
import kz.solva.expensetracker.model.CurrencyCode;
import kz.solva.expensetracker.model.ExchangeRate;
import kz.solva.expensetracker.model.Limit;
import kz.solva.expensetracker.repository.ExchangeRatesRepository;
import kz.solva.expensetracker.repository.LimitRepository;
import kz.solva.expensetracker.service.api.LimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static kz.solva.expensetracker.model.CurrencyPair.USD_KZT;
import static kz.solva.expensetracker.model.CurrencyPair.USD_RUB;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class LimitServiceImpl implements LimitService {
    private final LimitRepository limitRepository;
    private final LimitMapper limitMapper;
    private final ExchangeRatesRepository exchangeRatesRepository;

    @Override
    @Transactional
    public LimitDto createLimit(Limit limit) {
        LocalDateTime now = LocalDateTime.now();
        CurrencyCode currencyCode = limit.getLimitCurrencyShortname();
        BigDecimal limitSum = limit.getLimitSum();
        BigDecimal convertedSum = checkCurrencyShortName(currencyCode, limitSum);


        Limit createdLimit = limitMapper.createLimit(convertedSum, now, currencyCode, limit.getUser().getId());
        Limit saved = limitRepository.save(createdLimit);
        LimitDto dto = limitMapper.toDto(saved);
        return dto;
    }



    private BigDecimal checkCurrencyShortName(CurrencyCode currencyCode, BigDecimal limitSum) {
        switch (currencyCode) {
            case KZT -> {
                ExchangeRate exchangeRates = exchangeRatesRepository.findByUpdatedAtAndCurrencyPair(USD_KZT)
                        .orElseThrow(() -> NotFoundException.builder()
                                .message("Exchange rate for USD/KZT not found")
                                .build());

                BigDecimal rate = exchangeRates.getRate();

                return limitSum.divide(rate, RoundingMode.HALF_UP);
            }
            case RUB -> {
                ExchangeRate exchangeRates = exchangeRatesRepository.findByUpdatedAtAndCurrencyPair(USD_RUB)
                        .orElseThrow(() -> NotFoundException.builder()
                                .message("Exchange rate for USD/RUB not found")
                                .build());
                BigDecimal rate = exchangeRates.getRate();

                return limitSum.divide(rate, RoundingMode.HALF_UP);
            }
            case USD -> {
                return limitSum;
            }
            default -> throw BadRequestException.builder()
                    .message(String.format("This currency: %s not supported", currencyCode))
                    .build();
        }
    }
}
