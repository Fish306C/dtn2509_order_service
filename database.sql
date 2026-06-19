CREATE DATABASE IF NOT EXISTS dtn2509_order_service;
USE dtn2509_order_service;

CREATE TABLE IF NOT EXISTS orders
(
    id                  VARCHAR(36)             NOT NULL PRIMARY KEY,
    customer_id         VARCHAR(255)            NOT NULL,
    status              VARCHAR(255)            NOT NULL,
    total_amount        INT                     NOT NULL,
    is_deleted          TINYINT(1) DEFAULT 0    NULL,
    created_date        TIMESTAMP(6)            NULL,
    created_by          VARCHAR(255)            NULL,
    last_modified_date  TIMESTAMP(6)            NULL,
    last_modified_by    VARCHAR(255)            NULL
);

CREATE TABLE IF NOT EXISTS order_items
(
    id                  VARCHAR(36)             NOT NULL PRIMARY KEY,
    order_id            VARCHAR(36)             NOT NULL,
    product_id          VARCHAR(255)            NOT NULL,
    price               INT                     NOT NULL,
    quantity            INT                     NOT NULL,
    is_deleted          TINYINT(1) DEFAULT 0    NULL,
    created_date        TIMESTAMP(6)            NULL,
    created_by          VARCHAR(255)            NULL,
    last_modified_date  TIMESTAMP(6)            NULL,
    last_modified_by    VARCHAR(255)            NULL,
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders (id)
);

INSERT INTO orders (
    id,
    customer_id,
    status,
    total_amount,
    is_deleted,
    created_date,
    created_by,
    last_modified_date,
    last_modified_by
) VALUES
(
    'c8f3b23a-6bb1-11f1-bb4a-0a0027000005',
    'cust-123',
    'COMPLETED',
    150000,
    0,
    NOW(6),
    'system_admin',
    NOW(6),
    'system_admin'
),
(
    'c8f3b9a5-6bb1-11f1-bb4a-0a0027000005',
    'cust-456',
    'PENDING',
    230000,
    0,
    NOW(6),
    'customer_user',
    NOW(6),
    'customer_user'
);

INSERT INTO order_items (
    id,
    order_id,
    product_id,
    price,
    quantity,
    is_deleted,
    created_date,
    created_by,
    last_modified_date,
    last_modified_by
) VALUES
(
    UUID(),
    'c8f3b23a-6bb1-11f1-bb4a-0a0027000005',
    'product-A',
    100000,
    1,
    0,
    NOW(6),
    'system_admin',
    NOW(6),
    'system_admin'
),
(
    UUID(),
    'c8f3b23a-6bb1-11f1-bb4a-0a0027000005',
    'product-B',
    25000,
    2,
    0,
    NOW(6),
    'system_admin',
    NOW(6),
    'system_admin'
),
(
    UUID(),
    'c8f3b9a5-6bb1-11f1-bb4a-0a0027000005',
    'product-C',
    230000,
    1,
    0,
    NOW(6),
    'customer_user',
    NOW(6),
    'customer_user'
);
