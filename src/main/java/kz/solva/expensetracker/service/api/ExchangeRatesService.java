package kz.solva.expensetracker.service.api;

public interface ExchangeRatesService {
    /*
    Обновляет с 9:00 по 15:00 каждый час акутальный курс валют
     */
    void syncDailyCurrencyRates();
}
