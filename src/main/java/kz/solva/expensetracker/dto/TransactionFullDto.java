package kz.solva.expensetracker.dto;

import kz.solva.expensetracker.model.CurrencyCode;
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
public class TranslationFullDto implements Serializable {
    private Long id;
    private String accountFrom;
    private String accountTo;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime datetime;
    private Boolean limitExceeded;
    private Long limitId;
    private BigDecimal limitSum;
    private String currencyShortname;
}
