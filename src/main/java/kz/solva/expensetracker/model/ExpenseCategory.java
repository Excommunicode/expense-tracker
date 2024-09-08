package kz.solva.expensetracker.model;

import lombok.Getter;

@Getter
public enum ExpenseCategory {
    GOODS("Товары"),
    SERVICES("Услуги");

    private final String description;


    ExpenseCategory(String description) {
        this.description = description;

    }
}

