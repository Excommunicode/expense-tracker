package kz.solva.expensetracker.mapper;

import kz.solva.expensetracker.dto.LimitDto;
import kz.solva.expensetracker.model.CurrencyCode;
import kz.solva.expensetracker.model.Limit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static kz.solva.expensetracker.constant.Constant.DATA_TIME_FORMATTER;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LimitMapper {

    @Mapping(target = "limitDatetime", source = "limitDatetime", dateFormat = DATA_TIME_FORMATTER)
    Limit toEntity(LimitDto limitDto);

    @Mapping(target = "limitDatetime", source = "limitDatetime", dateFormat = DATA_TIME_FORMATTER)
    LimitDto toDto(Limit limit);

    @Mapping(target = "user.id", source = "userId")
    Limit createLimit(BigDecimal limitSum, LocalDateTime limitDatetime, CurrencyCode limitCurrencyShortname, Long userId);
}