CREATE TABLE model_simple_positions
(
    uuid                         UUID UNIQUE DEFAULT uuid_generate_v4(),
    portfolio_uuid               UUID       NOT NULL,
    parent_complex_position_uuid UUID,
    ticker                       VARCHAR(8) NOT NULL,
    share                        NUMERIC,

    PRIMARY KEY (uuid),
    CONSTRAINT check_size CHECK (share > 0 AND share < 1),
    FOREIGN KEY (portfolio_uuid) REFERENCES portfolios (uuid),
    FOREIGN KEY (parent_complex_position_uuid) REFERENCES complex_positions (uuid)
);