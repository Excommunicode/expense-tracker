package kz.solva.expensetracker.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotFoundException extends RuntimeException {
    private final String message;
}