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
@Table(name = "limits")
@Builder(toBuilder = true)
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limit_sum", nullable = false)
    private BigDecimal limitSum;

    @Column(name = "limit_datetime", nullable = false)
    private LocalDateTime limitDatetime;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "limit_currency_shortname", nullable = false)
    private CurrencyCode limitCurrencyShortname;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Limit limit = (Limit) o;
        return Objects.equals(getId(), limit.getId()) && Objects.equals(getLimitSum(), limit.getLimitSum()) && Objects.equals(getLimitDatetime(), limit.getLimitDatetime()) && Objects.equals(getLimitCurrencyShortname(), limit.getLimitCurrencyShortname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLimitSum(), getLimitDatetime(), getLimitCurrencyShortname());
    }

    @Override
    public String toString() {
        return "Limit{" +
                "id=" + id +
                ", limitSum=" + limitSum +
                ", limitDatetime=" + limitDatetime +
                ", limitCurrencyShortname=" + limitCurrencyShortname +
                ", category=" + category +
                ", user=" + user +
                '}';
    }
}
