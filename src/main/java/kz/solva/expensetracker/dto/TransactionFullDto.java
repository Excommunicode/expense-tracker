package kz.solva.expensetracker.dto;

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
public class TransactionFullDto implements Serializable {
    private Long id;
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal sum;
    private String expenseCategory;
    private String datetime;
    private Boolean limitExceeded;
    private Long limitId;
    private BigDecimal limitSum;
    private String currencyShortname;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionFullDto that = (TransactionFullDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAccountFrom(), that.getAccountFrom()) && Objects.equals(getAccountTo(), that.getAccountTo()) && Objects.equals(getSum(), that.getSum()) && Objects.equals(getExpenseCategory(), that.getExpenseCategory()) && Objects.equals(getDatetime(), that.getDatetime()) && Objects.equals(getLimitExceeded(), that.getLimitExceeded()) && Objects.equals(getLimitId(), that.getLimitId()) && Objects.equals(getLimitSum(), that.getLimitSum()) && Objects.equals(getCurrencyShortname(), that.getCurrencyShortname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountFrom(), getAccountTo(), getSum(), getExpenseCategory(), getDatetime(), getLimitExceeded(), getLimitId(), getLimitSum(), getCurrencyShortname());
    }
}
