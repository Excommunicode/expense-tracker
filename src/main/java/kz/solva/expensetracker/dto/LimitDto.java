package kz.solva.expensetracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LimitDto implements Serializable {

    private Long id;

    @NotNull(message = "Limit sum must not be null.")
    @Positive(message = "Limit sum must be positive.")
    private BigDecimal limitSum;

    @NotNull(message = "Limit datetime must not be null.")
    private String limitDatetime;

    @NotNull(message = "Limit currency shortname must not be null.")
    @Size(min = 3, max = 3, message = "Limit currency shortname must be exactly 3 characters long.")
    private String limitCurrencyShortname;
}
