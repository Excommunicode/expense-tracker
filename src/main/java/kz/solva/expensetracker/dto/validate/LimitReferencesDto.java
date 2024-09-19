package kz.solva.expensetracker.dto.validate;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LimitReferencesDto {
    @NotEmpty(message = "limitsIds cannot be empty")
    private List<Long> limitsIds;
}
