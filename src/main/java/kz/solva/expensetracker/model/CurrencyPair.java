package kz.solva.expensetracker.model;

import lombok.Getter;

@Getter
public enum CurrencyPair {
    USD_KZT("USD/KZT"),
    USD_RUB("USD/RUB"),
    KZT_USD("KZT/USD"),
    RUB_USD("RUB/USD");

    private final String pair;

    CurrencyPair(String pair) {
        this.pair = pair;
    }

}
