CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS stock_prices
(
    uuid         UUID UNIQUE DEFAULT uuid_generate_v4(),
    ticker       VARCHAR(64) NOT NULL,
    price        NUMERIC NOT NULL,
    decimals     INTEGER DEFAULT 2,
    lot_size      INTEGER DEFAULT 1

    PRIMARY KEY (uuid)
);
