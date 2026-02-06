CREATE TABLE products
(
    id       INTEGER NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    price    NUMERIC(10, 2) DEFAULT 0.0 NOT NULL,
    vat      NUMERIC(4, 2) NOT NULL DEFAULT 0.22,
    deteled BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY (id)
);


CREATE TABLE orders
(
    id       INTEGER NOT NULL AUTO_INCREMENT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_price    NUMERIC(10, 2) DEFAULT 0.0 NOT NULL,
    total_vat      NUMERIC(4, 2) NOT NULL DEFAULT 0.22,
    deteled BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY (id)
);

CREATE TABLE order_products
(
    id       INTEGER NOT NULL AUTO_INCREMENT,
    price    NUMERIC(10, 2) DEFAULT 0.0 NOT NULL,
    vat      NUMERIC(4, 2) NOT NULL DEFAULT 0.22,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    deteled BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES products(id),
    CONSTRAINT fk_order FOREIGN KEY(order_id) REFERENCES orders(id)
)