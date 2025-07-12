CREATE TYPE ProductStatus as ENUM ('ACTIVE', 'INACTIVE', 'DELETED');
CREATE TABLE products
(
    id          bigint       NOT NULL UNIQUE,
    name        varchar(255) NOT NULL,
    slug        varchar(255) UNIQUE,
    sku         varchar(20) UNIQUE,
    gtin        varchar(20),
    has_variant boolean      NOT NULL DEFAULT (false),
    parent_id   bigint,
    price       money,
    supplier    varchar(32),
    brand       integer,
    is_featured boolean               DEFAULT (false),
    description text,

    PRIMARY KEY (id)
);
CREATE INDEX idx_product_slug ON products (slug);

CREATE TABLE product_categories
(
    id            bigint      NOT NULL UNIQUE,
    product_id    bigint      NOT NULL,
    category_id   varchar(32) NOT NULL,
    display_order integer     NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_product_product_categories_542b43n4 FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_product_product_categories_7c2b43n4 FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE product_images
(
    id         bigint       NOT NULL UNIQUE,
    product_id bigint       NOT NULL,
    image_path varchar(255) NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_product_product_images_672b43n4 FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE product_attributes
(
    id   bigint      NOT NULL UNIQUE,
    name varchar(50) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE product_attribute_values
(
    id                   bigint       NOT NULL UNIQUE,
    product_id           bigint       NOT NULL,
    product_attribute_id bigint       NOT NULL,
    value                varchar(100) NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_product_values_426y43p5 FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_attribute_values_426y43p5 FOREIGN KEY (product_attribute_id) REFERENCES product_attributes (id)
);
CREATE INDEX idx_product_attribute_values_4523763a ON product_attribute_values (product_id);
