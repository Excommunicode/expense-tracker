package kz.solva.expensetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.solva.expensetracker.dto.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.dto.LimitReferencesDto;
import kz.solva.expensetracker.mapper.TransactionMapper;
import kz.solva.expensetracker.model.Transaction;
import kz.solva.expensetracker.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@WebMvcTest(TransactionController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TransactionControllerTest {
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransactionMapper transactionMapper;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;


    private static final Long DEFAULT_ID = 1L;
    private static final Long DEFAULT_ACCOUNT_FROM = 100L;
    private static final Long DEFAULT_ACCOUNT_TO = 200L;
    private static final String DEFAULT_CURRENCY_SHORTNAME = "USD";
    private static final BigDecimal DEFAULT_SUM = new BigDecimal("1000.0");
    private static final String DEFAULT_EXPENSE_CATEGORY = "Travel";
    private static final String DEFAULT_DATETIME = LocalDateTime.now().toString();
    private static final Boolean DEFAULT_LIMIT_EXCEEDED = false;
    private static final Long DEFAULT_LIMIT_ID = 1L;

    private final TransactionDto transactionDto = TransactionDto.builder()
            .id(DEFAULT_ID)
            .accountFrom(DEFAULT_ACCOUNT_FROM)
            .accountTo(DEFAULT_ACCOUNT_TO)
            .currencyShortname(DEFAULT_CURRENCY_SHORTNAME)
            .sum(DEFAULT_SUM)
            .expenseCategory(DEFAULT_EXPENSE_CATEGORY)
            .datetime(DEFAULT_DATETIME)
            .limitExceeded(DEFAULT_LIMIT_EXCEEDED)
            .limitId(DEFAULT_LIMIT_ID)
            .build();

    private final Transaction transaction = new Transaction();

    private final TransactionDto responseDto = TransactionDto.builder()
            .id(DEFAULT_ID)
            .accountFrom(DEFAULT_ACCOUNT_FROM)
            .accountTo(DEFAULT_ACCOUNT_TO)
            .currencyShortname(DEFAULT_CURRENCY_SHORTNAME)
            .sum(DEFAULT_SUM)
            .expenseCategory(DEFAULT_EXPENSE_CATEGORY)
            .datetime(DEFAULT_DATETIME)
            .limitExceeded(DEFAULT_LIMIT_EXCEEDED)
            .limitId(DEFAULT_LIMIT_ID)
            .build();


    @Test
    @SneakyThrows
    void createTransactionTest() {
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(transactionService.addTransaction(transaction)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountFrom").value(DEFAULT_ACCOUNT_FROM))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountTo").value(DEFAULT_ACCOUNT_TO))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyShortname").value(DEFAULT_CURRENCY_SHORTNAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sum").value(DEFAULT_SUM))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expenseCategory").value(DEFAULT_EXPENSE_CATEGORY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datetime").value(DEFAULT_DATETIME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limitExceeded").value(DEFAULT_LIMIT_EXCEEDED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limitId").value(DEFAULT_LIMIT_ID));
    }

    @Test
    @SneakyThrows
    void findExceededTransactionTest() {

        List<Long> limitsIds = Arrays.asList(1L, 2L, 3L);

        List<TransactionFullDto> testTransactionFullDtos = new ArrayList<>();


        when(transactionService.findExceededTransaction(any(LimitReferencesDto.class))).thenReturn(testTransactionFullDtos);


        String limitsIdsQuery = limitsIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions")
                        .param("limitsIds", limitsIdsQuery)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testTransactionFullDtos)));
    }


}
