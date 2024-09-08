package kz.solva.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LimitDto implements Serializable {
    private Long id;
    private BigDecimal limitSum;
    private LocalDateTime limitDatetime;
    private String limitCurrencyShortname;
}