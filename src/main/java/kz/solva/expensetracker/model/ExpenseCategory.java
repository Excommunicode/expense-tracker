package kz.solva.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpenseCategory {
    GOODS("Товары"),
    SERVICES("Услуги");

    private final String description;
}