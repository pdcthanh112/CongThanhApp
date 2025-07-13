CREATE TABLE IF NOT EXISTS inventories
(
    id    bigint      NOT NULL UNIQUE,
    sku   varchar(20) NOT NULL,
    stock integer     NOT NULL,

    PRIMARY KEY (id)
)