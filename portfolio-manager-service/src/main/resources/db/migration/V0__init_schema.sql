CREATE TABLE portfolios
(
    uuid          UUID UNIQUE,
    is_model      BOOLEAN,
    base_currency VARCHAR(8),
    name          VARCHAR(64) NOT NULL,

    PRIMARY KEY (uuid)
);

CREATE TABLE complex_positions
(
    uuid                         UUID UNIQUE,
    portfolio_uuid               UUID        NOT NULL,
    parent_complex_position_uuid UUID,
    description                  VARCHAR(64) NOT NULL,

    PRIMARY KEY (uuid),
    FOREIGN KEY (portfolio_uuid) REFERENCES portfolios (uuid)
);

CREATE TABLE client_simple_positions
(
    uuid                         UUID UNIQUE,
    portfolio_uuid               UUID       NOT NULL,
    parent_complex_position_uuid UUID,
    ticker                       VARCHAR(8) NOT NULL,
    size                         INT,

    PRIMARY KEY (uuid),
    CONSTRAINT check_size CHECK (size > 0),
    FOREIGN KEY (portfolio_uuid) REFERENCES portfolios (uuid),
    FOREIGN KEY (parent_complex_position_uuid) REFERENCES complex_positions (uuid)
);