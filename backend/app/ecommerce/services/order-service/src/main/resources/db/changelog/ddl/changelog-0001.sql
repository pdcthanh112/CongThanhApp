CREATE TYPE OrderStatus AS ENUM ('CREATED', 'PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELED', 'REFUNDED', 'RETURNED', 'COMPLETED')

CREATE TABLE "orders"
(
    id            bigint      NOT NULL UNIQUE,
    order_code    varchar(20) NOT NULL UNIQUE,
    customer_id   varchar(32) NOT NULL,
    note          text,
    total_amount  money       NOT NULL,
    currency_code varchar(3)  NOT NULL,
    order_date    bigint      NOT NULL,
    status        OrderStatus NOT NULL DEFAULT ('CREATED'),

    PRIMARY KEY (id)
);

CREATE TABLE order_items
(
    id          bigint  NOT NULL UNIQUE,
    product_id  bigint  NOT NULL,
    quantity    integer NOT NULL CHECK (quantity >= 0),
    order_price money   NOT NULL CHECK ( order_price > 0 ),
    order_id    bigint  NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_order_order_item_31d2t45y FOREIGN KEY (order_id) REFERENCES "orders" (id)
);

CREATE TABLE order_status_tracking
(
    id          bigint      NOT NULL UNIQUE,
    order_id    bigint      NOT NULL,
    step_order  integer     NOT NULL,
    description text,
    note        text,
    updated_at  bigint      NOT NULL,
    updated_by  varchar(32) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_order_order_tracking_426y43p5 FOREIGN KEY (order_id) REFERENCES "orders" (id)
)