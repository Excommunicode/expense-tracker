package kz.solva.expensetracker.mapper;

import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.dto.validate.TransactionDto;
import kz.solva.expensetracker.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static kz.solva.expensetracker.constant.Constant.DATA_TIME_FORMATTER;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {


    @Mapping(target = "datetime", source = "datetime", dateFormat = DATA_TIME_FORMATTER)
    Transaction toEntity(TransactionDto transactionDto);


    @Mapping(target = "datetime", source = "datetime", dateFormat = DATA_TIME_FORMATTER)
    TransactionDto toDto(Transaction transaction);

    TransactionFullDto toFullDto(Transaction transaction);
}