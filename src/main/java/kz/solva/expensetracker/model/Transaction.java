package kz.solva.expensetracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_from", nullable = false)
    private User accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_to", nullable = false)
    private User accountTo;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "currency_shortname", nullable = false)
    private CurrencyCode currencyShortname;

    @Column(nullable = false)
    private BigDecimal sum;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "expense_category")
    private ExpenseCategory expenseCategory;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(name = "limit_exceeded")
    private Boolean limitExceeded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "limit_id")
    private Limit limit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAccountFrom(), that.getAccountFrom()) && Objects.equals(getAccountTo(), that.getAccountTo()) && Objects.equals(getCurrencyShortname(), that.getCurrencyShortname()) && Objects.equals(getSum(), that.getSum()) && Objects.equals(getExpenseCategory(), that.getExpenseCategory()) && Objects.equals(getDatetime(), that.getDatetime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountFrom(), getAccountTo(), getCurrencyShortname(), getSum(), getExpenseCategory(), getDatetime());
    }

}
