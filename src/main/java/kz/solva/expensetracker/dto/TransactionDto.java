package kz.solva.expensetracker.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionDto implements Serializable {
    private Long id;
    @NotNull
    private String accountFrom;
    @NotNull
    private String accountTo;
    @NotNull
    private String currencyShortname;
    @NotNull
    private String sum;
    @NotNull
    private String expenseCategory;
    private LocalDateTime datetime;
}