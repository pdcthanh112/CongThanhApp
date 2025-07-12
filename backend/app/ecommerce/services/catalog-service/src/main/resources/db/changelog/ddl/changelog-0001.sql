CREATE TABLE country
(
    id                  bigint      NOT NULL UNIQUE,
    country_code        varchar(3)  NOT NULL UNIQUE,
    country_name        varchar(50) NOT NULL,
    country_name_origin varchar     NOT NULL,
    currency_code       varchar(20) NOT NULL,
    currency_symbol     varchar(5),
    iso_code            varchar(2)  NOT NULL UNIQUE,
    iso_code_3          varchar(3)  NOT NULL UNIQUE,
    language            varchar(20),
    locale              varchar(2),
    phone_code          varchar(4),

    PRIMARY KEY (id)
);

CREATE TYPE CategoryStatus as ENUM ('ACTIVE', 'INACTIVE')
CREATE TABLE categories
(
    id        varchar(32)    NOT NULL UNIQUE,
    name      varchar(255)   NOT NULL,
    slug      varchar(50)    NOT NULL UNIQUE,
    image     varchar(255),
    parent_id varchar(32),
    status    CategoryStatus NOT NULL DEFAULT ('ACTIVE'),

    PRIMARY KEY (id)
);

CREATE TYPE BrandStatus as ENUM ('ACTIVE', 'INACTIVE')
CREATE TABLE Brands
(
    id     bigint       NOT NULL UNIQUE,
    name   varchar(255) NOT NULL,
    slug   varchar(50)  NOT NULL UNIQUE,
    image  varchar(255),
    status BrandStatus  NOT NULL DEFAULT ('ACTIVE'),

    PRIMARY KEY (id)
);

CREATE TYPE TagStatus as ENUM ('ACTIVE', 'INACTIVE')
CREATE TABLE Tags
(
    id     bigint    NOT NULL UNIQUE,
    name   varchar(255),
    status TagStatus NOT NULL DEFAULT ('ACTIVE'),

    PRIMARY KEY (id)
)