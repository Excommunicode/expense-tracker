# expense-tracker

## Описание проекта
"Expense Tracker" — это микросервисное приложение, являющееся частью банковской системы. 
Оно предназначено для управления транзакциями между пользователями и поддерживает работу с любой валютой. 
Микросервис автоматически получает информацию о текущих валютных курсах и позволяет устанавливать лимиты
на расходы для каждого пользователя.

## Инструкция по развертыванию проекта

### 1. Клонирование репозитория

Войдите в нужную директорию на своём компьютере и выполните команду:
git clone https://github.com/Excommunicode/expense-tracker.git

### 2. Сборка проекта

Запустите проект. Файл compose.yaml автоматически загрузится, и будут созданы необходимые контейнеры для работы программы.
Проверьте, что программа запустилась и работает корректно.

### 3. Запуск тестов

Запустите тесты проекта с помощью команды mvn test. Тесты используют встроенную базу данных H2 для проверки функциональности.
Убедитесь, что все тесты прошли успешно.

### 4. Доступ к приложению

Приложение будет доступно на локальной машине по адресу http://localhost:8080.
При необходимости вы можете изменить порт в файле конфигурации.


## Системные требования

Для корректной работы проекта требуется следующая конфигурация системы:

- Операционная система: Windows 10/11, macOS Monterey или Linux (с поддержкой Docker)
- Процессор: 4-ядерный процессор, например Intel Core i5 или AMD Ryzen 5
- Оперативная память: минимум 8 GB RAM
- Свободное место на диске: минимум 2 GB для хранения Docker контейнеров и данных
- Интернет-соединение: для получения курсов валют через сторонние API


## Стек технологий
Проект использует следующие технологии:

- Java 21 (Amazon Corretto)
- Spring Boot 3.3.3 (для создания микросервисов)
- PostgreSQL (база данных)
- Docker (для контейнеризации и деплоя)
- Maven (управление зависимостями и сборка проекта)
- GitHub Actions (для настройки CI/CD)

## Планы по доработке
* Добавление возможностей для автоматической конвертации валют по заданным курсам.
* Подключение к сторонним API для получения более точных данных по обменным курсам.
* Интеграция с системами уведомлений для информирования пользователей о превышении лимитов.


## Доступ к Swagger спецификации
Для просмотра и взаимодействия с API вы также можете воспользоваться Swagger UI по [адресу](https://app.swaggerhub.com/apis/faruh2806/expense-tracker/v0)
