package kz.solva.expensetracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.solva.expensetracker.model.CurrencyPair;
import kz.solva.expensetracker.model.ExchangeRate;
import kz.solva.expensetracker.repository.ExchangeRatesRepository;
import kz.solva.expensetracker.service.api.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static kz.solva.expensetracker.model.CurrencyCode.KZT;
import static kz.solva.expensetracker.model.CurrencyCode.RUB;
import static kz.solva.expensetracker.model.CurrencyPair.KZT_USD;
import static kz.solva.expensetracker.model.CurrencyPair.RUB_USD;
import static kz.solva.expensetracker.model.CurrencyPair.USD_KZT;
import static kz.solva.expensetracker.model.CurrencyPair.USD_RUB;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${exchange.rate.api.url.usd}")
    private String exchangeRateUsd;

    @Value("${exchange.rate.api.url.kzt}")
    private String exchangeRateKzt;

    @Override
    @SneakyThrows
    @Transactional
    @Scheduled(cron = "0 0 9,15 * * *")
    public void syncDailyCurrencyRates() {
        LocalDateTime now = LocalDateTime.now();


        String jsonResponseRateUsd = restTemplate.getForObject(exchangeRateUsd, String.class);
        String jsonResponseRateKzt = restTemplate.getForObject(exchangeRateKzt, String.class);

        JsonNode jsonNodeRateUsd = objectMapper.readTree(jsonResponseRateUsd);
        JsonNode jsonNodeRateKzt = objectMapper.readTree(jsonResponseRateKzt);

        double usdKzt = jsonNodeRateUsd.path("conversion_rates").path(KZT.getCode()).asDouble();
        double usdRub = jsonNodeRateUsd.path("conversion_rates").path(RUB.getCode()).asDouble();

        double kztUsd = jsonNodeRateKzt.path("conversion_rates").path(KZT.getCode()).asDouble();
        double rubUsd = jsonNodeRateKzt.path("conversion_rates").path(RUB.getCode()).asDouble();

        saveExchangeRate(USD_KZT, usdKzt, now);
        saveExchangeRate(USD_RUB, usdRub, now);
        saveExchangeRate(KZT_USD, kztUsd, now);
        saveExchangeRate(RUB_USD, rubUsd, now);
    }

    private void saveExchangeRate(CurrencyPair currencyPair, double rate, LocalDateTime updatedAt) {
        log.info("Saving {} rate: {}", currencyPair, rate);
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .currencyPair(currencyPair)
                .rate(BigDecimal.valueOf(rate))
                .updatedAt(updatedAt)
                .build();
        exchangeRatesRepository.save(exchangeRate);
        log.info("Successfully saved {} rate.", currencyPair);
    }

}
