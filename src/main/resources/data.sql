INSERT INTO products (id, name, price, vat) VALUES (1, 'Product 1', 10.00, 0.22);
INSERT INTO products (id, name, price, vat) VALUES (2, 'Product 2', 18.00, 0.22);
INSERT INTO products (id, name, price, vat) VALUES (3, 'Product 3', 120.00, 0.22);

INSERT INTO orders (id, created_at) VALUES (1, CURRENT_TIMESTAMP);

INSERT INTO order_products (id, price, vat, order_id, product_id) VALUES (1, 10.0, 0.22, 1, 1);
INSERT INTO order_products (id, price, vat, order_id, product_id) VALUES (2, 18.0, 0.22, 1, 2);
