CREATE TABLE portfolios
(
    id   SERIAL UNIQUE,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE simple_positions
(
    id                         SERIAL UNIQUE,
    portfolio_id               INT        NOT NULL,
    parent_complex_position_id INT,
    ticker                     VARCHAR(8) NOT NULL,
    size                       INT,

    PRIMARY KEY (id),
    FOREIGN KEY (portfolio_id) REFERENCES portfolios (id),
    FOREIGN KEY (parent_complex_position_id) REFERENCES complex_positions (id),
    CHECK (size > 0)
);

CREATE TABLE complex_positions
(
    id                         SERIAL UNIQUE,
    portfolio_id               INT         NOT NULL,
    parent_complex_position_id INT,
    description                VARCHAR(64) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (portfolio_id) REFERENCES portfolios (id)
);

-- Change all ids to UUID