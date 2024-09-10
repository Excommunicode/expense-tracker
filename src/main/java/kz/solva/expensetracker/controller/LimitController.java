package kz.solva.expensetracker.controller;

import jakarta.validation.Valid;
import kz.solva.expensetracker.dto.LimitDto;
import kz.solva.expensetracker.mapper.LimitMapper;
import kz.solva.expensetracker.model.Limit;
import kz.solva.expensetracker.service.api.LimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/limits")
public class LimitController {
    private final LimitService limitService;
    private final LimitMapper limitMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LimitDto createLimit( @RequestBody LimitDto limitDto) {
        log.info("Endpoint /api/v1/limits POST started. Received request to create limit: {}", limitDto);
        Limit entity = limitMapper.toEntity(limitDto);
        return limitService.createLimit(entity);
    }
}
