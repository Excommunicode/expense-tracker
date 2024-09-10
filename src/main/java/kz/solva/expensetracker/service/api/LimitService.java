package kz.solva.expensetracker.service.api;

import kz.solva.expensetracker.dto.LimitDto;
import kz.solva.expensetracker.model.Limit;

public interface LimitService {
    LimitDto    createLimit(Limit limit);

}
