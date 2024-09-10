package kz.solva.expensetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.solva.expensetracker.dto.LimitDto;
import kz.solva.expensetracker.mapper.LimitMapper;
import kz.solva.expensetracker.model.Limit;
import kz.solva.expensetracker.service.api.LimitService;
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

import static org.mockito.Mockito.when;

@WebMvcTest(LimitController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LimitControllerTest {
    @MockBean
    private LimitService limitService;
    @MockBean
    private LimitMapper limitMapper;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    private static final Long DEFAULT_ID = 1L;
    private static final BigDecimal DEFAULT_LIMIT_SUM = new BigDecimal("1000.00");
    private static final LocalDateTime DEFAULT_LIMIT_DATETIME = LocalDateTime.now();
    private static final String DEFAULT_CURRENCY_SHORTNAME = "USD";

    private final LimitDto limitDto = LimitDto.builder()
            .id(DEFAULT_ID)
            .limitSum(DEFAULT_LIMIT_SUM)
            .limitDatetime(DEFAULT_LIMIT_DATETIME.toString())
            .limitCurrencyShortname(DEFAULT_CURRENCY_SHORTNAME)
            .build();

    private final Limit limitEntity = new Limit();


    private final LimitDto responseDto = LimitDto.builder()
            .id(DEFAULT_ID)
            .limitSum(DEFAULT_LIMIT_SUM)
            .limitDatetime(DEFAULT_LIMIT_DATETIME.toString())
            .limitCurrencyShortname(DEFAULT_CURRENCY_SHORTNAME)
            .build();


    @Test
    @SneakyThrows
    void createLimit() {
        when(limitMapper.toEntity(limitDto)).thenReturn(limitEntity);
        when(limitService.createLimit(limitEntity)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(limitDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limitSum").value(BigDecimal.valueOf(1000.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limitCurrencyShortname").value("USD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limitDatetime").value(DEFAULT_LIMIT_DATETIME.toString()));
    }
}