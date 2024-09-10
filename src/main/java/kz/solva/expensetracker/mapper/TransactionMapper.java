package kz.solva.expensetracker.mapper;

import kz.solva.expensetracker.dto.TransactionDto;
import kz.solva.expensetracker.dto.TransactionFullDto;
import kz.solva.expensetracker.model.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "accountFrom.id", source = "accountFrom"),
            @Mapping(target = "accountTo.id", source = "accountTo")
    })
    Transaction toEntity(TransactionDto transactionDto);

    @Mappings({
            @Mapping(target = "accountFrom", source = "accountFrom.id"),
            @Mapping(target = "accountTo", source = "accountTo.id")
    })
    TransactionDto toDto(Transaction transaction);

    @Mappings({
            @Mapping(target = "accountFrom", source = "accountFrom.id"),
            @Mapping(target = "accountTo", source = "accountTo.id")
    })
    TransactionFullDto toFullDto(Transaction transaction);


    List<TransactionFullDto> toFullDtoList(List<Transaction> transactions);
}