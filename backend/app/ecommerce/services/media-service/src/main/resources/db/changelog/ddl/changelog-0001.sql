CREATE TYPE MediaType AS ENUM ('PRODUCT', 'REVIEW', 'SHIPPING_CONFIRM');

CREATE TABLE IF NOT EXISTS media_files
(
    id           bigint       NOT NULL UNIQUE,
    media_type   MediaType    NOT NULL,
    file_name    varchar(50)  NOT NULL,
    file_path    varchar(255) NOT NULL,
    reference_id bigint       NOT NULL,

    PRIMARY KEY (id, media_type)
) PARTITION BY LIST (media_type);

CREATE TABLE media_files_product PARTITION OF media_files FOR VALUES IN ('PRODUCT');

CREATE TABLE media_files_review PARTITION OF media_files FOR VALUES IN ('REVIEW');

CREATE TABLE media_files_shipping PARTITION OF media_files FOR VALUES IN ('SHIPPING_CONFIRM');

-- ALTER TABLE media_files ATTACH PARTITION media_files_video
--     FOR VALUES IN ('VIDEO');