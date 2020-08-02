ALTER TABLE IF EXISTS model_portfolios
    ADD COLUMN is_model BOOLEAN;

CREATE TABLE model_simple_positions
(
    uuid                         UUID UNIQUE,
    portfolio_uuid               UUID       NOT NULL,
    parent_complex_position_uuid UUID,
    ticker                       VARCHAR(8) NOT NULL,
    share                        NUMERIC,

    PRIMARY KEY (uuid),
    CONSTRAINT check_size CHECK (share > 0 AND share < 1),
    FOREIGN KEY (portfolio_uuid) REFERENCES portfolios (uuid),
    FOREIGN KEY (parent_complex_position_uuid) REFERENCES complex_positions (uuid)
);