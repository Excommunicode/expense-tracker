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
public class TransactionFullDto implements Serializable {
    private Long id;
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime datetime;
    private Boolean limitExceeded;
    private Long limitId;
    private BigDecimal limitSum;
    private String currencyShortname;
}
