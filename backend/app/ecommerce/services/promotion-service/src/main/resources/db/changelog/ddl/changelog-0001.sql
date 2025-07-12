CREATE TYPE PromotionType AS ENUM ('DISCOUNT', 'SALE', 'VOUCHER', 'BUY_ONE_GET_ONE', 'FLASH_SALE', 'FREE_SHIPPING', 'LOYALTY_PROGRAM', 'BUNDLE_DEAL', 'FIRST_PURCHASE_DISCOUNT', 'REFERRAL_DISCOUNT', 'SEASONAL_PROMOTION', 'CASHBACK');
CREATE TYPE PromotionStatus AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TABLE promotions
(
    id          bigint          NOT NULL UNIQUE,
    code        varchar(20)     NOT NULL UNIQUE,
    type        PromotionType   NOT NULL,
    value       integer         NOT NULL,
    usage_limit integer         NOT NULL,
    description text,
    start_date  bigint          NOT NULL,
    end_date    bigint          NOT NULL,
    status      PromotionStatus NOT NULL DEFAULT ('ACTIVE'),

    PRIMARY KEY (id)
);

CREATE TABLE promotion_usages
(
    id           bigint      NOT NULL UNIQUE,
    customer_id  varchar(32) NOT NULL,
    promotion_id bigint      NOT NULL,
    order_id     bigint      NOT NULL,
    used_at      bigint      NOT NULL,

    PRIMARY KEY (id)
)