package kz.solva.expensetracker.model;

import lombok.Getter;

@Getter
public enum CurrencyCode {
    USD("USD"),
    RUB("RUB"),
    KZT("KZT");

    private final String code;

    CurrencyCode(String code) {
        this.code = code;
    }

}
