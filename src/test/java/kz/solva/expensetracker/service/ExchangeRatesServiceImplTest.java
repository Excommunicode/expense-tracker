package kz.solva.expensetracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.solva.expensetracker.model.CurrencyPair;
import kz.solva.expensetracker.repository.ExchangeRatesRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesServiceImplTest {
    @Mock
    private ExchangeRatesRepository exchangeRatesRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ExchangeRatesServiceImpl exchangeRatesService;

    @Value("${exchange.rate.api.url.usd}")
    private String exchangeRateUsd;

    @Value("${exchange.rate.api.url.kzt}")
    private String exchangeRateKzt;

    @Test
    @SneakyThrows
    void syncDailyCurrencyRates() {
        String usdJsonResponse = "{\"conversion_rates\": {\"KZT\": 450.50, \"RUB\": 75.10}}";
        String kztJsonResponse = "{\"conversion_rates\": {\"KZT\": 450.50, \"RUB\": 75.10}}";


        when(restTemplate.getForObject(exchangeRateUsd, String.class)).thenReturn(usdJsonResponse);
        when(restTemplate.getForObject(exchangeRateKzt, String.class)).thenReturn(kztJsonResponse);

        JsonNode usdRates = new ObjectMapper().readTree(usdJsonResponse);
        JsonNode kztRates = new ObjectMapper().readTree(kztJsonResponse);
        when(objectMapper.readTree(usdJsonResponse)).thenReturn(usdRates);
        when(objectMapper.readTree(kztJsonResponse)).thenReturn(kztRates);

        exchangeRatesService.syncDailyCurrencyRates();

        verify(exchangeRatesRepository, times(1))
                .save(argThat(rate -> rate.getCurrencyPair() == CurrencyPair.USD_KZT && rate.getRate().equals(BigDecimal.valueOf(450.50))));
        verify(exchangeRatesRepository, times(1))
                .save(argThat(rate -> rate.getCurrencyPair() == CurrencyPair.USD_RUB && rate.getRate().equals(BigDecimal.valueOf(75.10))));
    }
}