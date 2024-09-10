package kz.solva.expensetracker.service;

import kz.solva.expensetracker.dto.LimitDto;
import kz.solva.expensetracker.mapper.LimitMapper;
import kz.solva.expensetracker.model.CurrencyPair;
import kz.solva.expensetracker.model.ExchangeRate;
import kz.solva.expensetracker.model.Limit;
import kz.solva.expensetracker.model.User;
import kz.solva.expensetracker.repository.ExchangeRatesRepository;
import kz.solva.expensetracker.repository.LimitRepository;
import kz.solva.expensetracker.repository.UserRepository;
import kz.solva.expensetracker.service.api.LimitService;
import lombok.RequiredArgsConstructor;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static kz.solva.expensetracker.model.CurrencyCode.KZT;
import static kz.solva.expensetracker.model.CurrencyCode.RUB;
import static kz.solva.expensetracker.model.CurrencyCode.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LimitServiceImplTest {
    private final LimitService limitService;
    private final LimitRepository limitRepository;
    private final LimitMapper limitMapper;
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final UserRepository userRepository;

    private Limit limit;
    private User user;

    @BeforeEach
    void setUp() {
        user = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .create();
        user = userRepository.save(user);

        limit = Instancio.of(Limit.class)
                .ignore(Select.field(Limit::getId))
                .set(Select.field(Limit::getLimitCurrencyShortname), USD)
                .set(Select.field(Limit::getUser), user)
                .create();

        ExchangeRate exchangeRateKzt = ExchangeRate.builder()
                .currencyPair(CurrencyPair.USD_KZT)
                .rate(BigDecimal.valueOf(450))
                .updatedAt(LocalDateTime.now())
                .build();
        exchangeRatesRepository.save(exchangeRateKzt);

        ExchangeRate exchangeRateRub = ExchangeRate.builder()
                .currencyPair(CurrencyPair.USD_RUB)
                .rate(BigDecimal.valueOf(90))
                .updatedAt(LocalDateTime.now())
                .build();
        exchangeRatesRepository.save(exchangeRateRub);
    }

    @Test
    void createLimit() {


        LimitDto saved = limitService.createLimit(limit);

        Limit limitFromDb = limitRepository.findById(saved.getId()).orElse(null);

        Limit expectedLimit = limitMapper.toEntity(saved);

        assertEquals(expectedLimit, limitFromDb);
    }


    @Test
    void createLimit_forKZT() {
        Limit limitForKZT = Instancio.of(Limit.class)
                .ignore(Select.field(Limit::getId))
                .set(Select.field(Limit::getLimitCurrencyShortname), KZT)
                .set(Select.field(Limit::getLimitSum), BigDecimal.valueOf(45000))
                .set(Select.field(Limit::getUser), user)
                .create();

        LimitDto saved = limitService.createLimit(limitForKZT);
        System.err.println(saved);
        Limit limitFromDb = limitRepository.findById(saved.getId()).orElse(null);

        Limit expectedLimit = limitMapper.toEntity(saved);

        assertEquals(expectedLimit, limitFromDb);
        assertEquals(expectedLimit.getLimitSum(), BigDecimal.valueOf(100));
    }

    @Test
    void createLimit_forUSD() {
        Limit limitForUSD = Instancio.of(Limit.class)
                .ignore(Select.field(Limit::getId))
                .set(Select.field(Limit::getLimitCurrencyShortname), RUB)
                .set(Select.field(Limit::getLimitSum), BigDecimal.valueOf(90000))
                .set(Select.field(Limit::getUser), user)
                .create();

        LimitDto saved = limitService.createLimit(limitForUSD);

        Limit limitFromDb = limitRepository.findById(saved.getId()).orElse(null);

        Limit expectedLimit = limitMapper.toEntity(saved);

        assertEquals(expectedLimit, limitFromDb);

        assertEquals(expectedLimit.getLimitSum(), BigDecimal.valueOf(1000));

    }
}
