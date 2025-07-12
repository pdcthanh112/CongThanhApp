CREATE TYPE PaymentStatus AS ENUM ('SUCCESS', 'PENDING', 'FAILED', 'CANCELLED', 'REFUNDED', 'PARTIALLY_REFUNDED', 'EXPIRED', 'PROCESSING', 'AUTHORIZED', 'CAPTURED', 'CHARGEBACK', 'DISPUTED');
CREATE TABLE payments
(
    id       bigint     NOT NULL UNIQUE,
    amount   money      NOT NULL,
    currency varchar(5) NOT NULL,
    status PaymentStatus NOT NULL ,

    PRIMARY KEY (id)
)