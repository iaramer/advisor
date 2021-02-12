CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS stock_prices
(
    ticker   VARCHAR(64) NOT NULL,
    exchange VARCHAR(64),
    price    NUMERIC NOT NULL,
    decimals INTEGER DEFAULT 2,
    lot_size INTEGER DEFAULT 1,

    PRIMARY KEY (ticker, exchange)
);
