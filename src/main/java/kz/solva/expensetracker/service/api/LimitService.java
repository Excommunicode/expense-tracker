package kz.solva.expensetracker.service.api;

import kz.solva.expensetracker.dto.validate.LimitDto;
import kz.solva.expensetracker.model.Limit;

public interface LimitService {
    LimitDto    createLimit(Limit limit);

}
