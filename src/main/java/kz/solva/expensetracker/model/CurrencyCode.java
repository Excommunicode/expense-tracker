package kz.solva.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyCode {
    USD("USD"),
    RUB("RUB"),
    KZT("KZT");

    private final String code;
}
