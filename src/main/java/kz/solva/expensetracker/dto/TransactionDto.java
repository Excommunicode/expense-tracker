package kz.solva.expensetracker.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionDto implements Serializable {

    private Long id;

    @NotNull(message = "Account from must not be null")
    private Long accountFrom;

    @NotNull(message = "Account to must not be null")
    private Long accountTo;

    @NotNull(message = "Currency shortname must not be null")
    @Size(min = 3, max = 3, message = "Currency shortname must be exactly 3 characters")
    private String currencyShortname;

    @NotNull(message = "Sum must not be null")
    @Digits(integer = 13, fraction = 2, message = "Sum must be a valid decimal number with up to 13 digits in the integer part and 2 decimal places")
    private BigDecimal sum;

    @NotNull(message = "Expense category must not be null")
    @Size(min = 1, message = "Expense category must not be empty")
    private String expenseCategory;

    private String datetime;

    private Boolean limitExceeded;

    private Long limitId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAccountFrom(), that.getAccountFrom()) && Objects.equals(getAccountTo(), that.getAccountTo()) && Objects.equals(getCurrencyShortname(), that.getCurrencyShortname()) && Objects.equals(getSum(), that.getSum()) && Objects.equals(getExpenseCategory(), that.getExpenseCategory()) && Objects.equals(getDatetime(), that.getDatetime()) && Objects.equals(getLimitExceeded(), that.getLimitExceeded()) && Objects.equals(getLimitId(), that.getLimitId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountFrom(), getAccountTo(), getCurrencyShortname(), getSum(), getExpenseCategory(), getDatetime(), getLimitExceeded(), getLimitId());
    }
}
