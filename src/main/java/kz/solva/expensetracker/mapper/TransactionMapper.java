package kz.solva.expensetracker.mapper;

import kz.solva.expensetracker.dto.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.model.Transaction;
import kz.solva.expensetracker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static kz.solva.expensetracker.constant.Constant.DATA_TIME_FORMATTER;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "accountFrom.id", source = "accountFrom"),
            @Mapping(target = "accountTo.id", source = "accountTo"),
            @Mapping(target = "datetime", source = "datetime", dateFormat = DATA_TIME_FORMATTER),


    })
    Transaction toEntity(TransactionDto transactionDto);

    @Mappings({
            @Mapping(target = "accountFrom", source = "accountFrom.id"),
            @Mapping(target = "accountTo", source = "accountTo.id"),
            @Mapping(target = "datetime", source = "datetime", dateFormat = DATA_TIME_FORMATTER)

    })
    TransactionDto toDto(Transaction transaction);

    default Long map(User user) {
        return user != null ? user.getId() : null;
    }

    List<TransactionFullDto> toFullDtoList(List<Transaction> transactions);
}