CREATE TABLE IF NOT EXISTS limits
(
    id                       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    limit_sum                DECIMAL(15, 2)           NOT NULL,
    limit_datetime           TIMESTAMP WITH TIME ZONE NOT NULL,
    limit_currency_shortname VARCHAR(3)               NOT NULL,
    category                 VARCHAR(50),
    user_id                  BIGINT                   NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    account_from       BIGINT                   NOT NULL,
    account_to         BIGINT                   NOT NULL,
    currency_shortname VARCHAR(3)               NOT NULL,
    sum                DECIMAL(15, 2)           NOT NULL,
    expense_category   VARCHAR(255),
    datetime           TIMESTAMP WITH TIME ZONE NOT NULL,
    limit_exceeded     BOOLEAN DEFAULT FALSE,
    limit_id           BIGINT,
    FOREIGN KEY (limit_id) REFERENCES limits (id) ON DELETE CASCADE
);
