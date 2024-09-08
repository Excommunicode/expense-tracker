package kz.solva.expensetracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "exchange_rates")
public class ExchangeRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_pair", nullable = false)
    private String currencyPair;

    @Column(nullable = false)
    private BigDecimal rate;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRates that = (ExchangeRates) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCurrencyPair(), that.getCurrencyPair()) && Objects.equals(getRate(), that.getRate()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCurrencyPair(), getRate(), getUpdatedAt());
    }
}
