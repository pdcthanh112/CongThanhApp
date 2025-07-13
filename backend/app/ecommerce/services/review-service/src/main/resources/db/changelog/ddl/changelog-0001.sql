CREATE TYPE RatingValue AS ENUM (1, 2, 3, 4, 5);
CREATE TABLE IF NOT EXISTS reviews
(
    id         bigint      NOT NULL UNIQUE,
    content    text,
    rating     RatingValue NOT NULL,
    author     varchar(32) NOT NULL,
    product_id bigint      NOT NULL,
    variant_id bigint      NOT NULL,
    order_id   bigint      NOT NULL,
    created_at bigint      NOT NULL,

    PRIMARY KEY (id)
)