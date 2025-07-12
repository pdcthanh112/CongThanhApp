CREATE TABLE carts
(
    id         bigint NOT NULL UNIQUE,

    created_at bigint,
    created_by varchar(32),
    updated_at bigint,
    updated_by varchar(32),

    PRIMARY KEY (id)
);

CREATE TABLE cart_items
(
    id         bigint  NOT NULL UNIQUE,
    cart_id    bigint  NOT NULL,
    quantity   integer NOT NULL CHECK ( quantity > 0 ),
    created_at bigint,
    created_by varchar(32),
    updated_at bigint,
    updated_by varchar(32),

    PRIMARY KEY (id),
    CONSTRAINT fk_cart_cart_item_46k23l47 foreign key (cart_id) references carts;
);


