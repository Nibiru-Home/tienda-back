-- Categories
INSERT INTO categories (id,name) VALUES (1, 'Iluminacion');
INSERT INTO categories (id,name) VALUES (2, 'Muebles');
INSERT INTO categories (id,name) VALUES (3, 'Decoracion');
INSERT INTO categories (id,name) VALUES (4, 'Textiles');
INSERT INTO categories (id,name) VALUES (5, 'Organizadores');

-- Users
INSERT INTO users (id, name, email, password, phone, address, role) VALUES (1, 'Alfonso', 'alfonso@example.com', 'alfonso123', '696781526', 'C/', 'ADMIN');
INSERT INTO users (id, name, email, password, phone, address, role) VALUES (2, 'Marta', 'marta@example.com', 'marta123', '694518796', 'C/', 'CUSTOMER');
INSERT INTO users (id, name, email, password, phone, address, role) VALUES (3, 'Ignacio', 'ignacio@example.com', 'ignacio123', '696718256', 'C/', 'CUSTOMER');
INSERT INTO users (id, name, email, password, phone, address, role) VALUES (4, 'Sonia', 'sonia@example.com', 'sonia123', '697814256', 'C/', 'ADMIN');
INSERT INTO users (id, name, email, password, phone, address, role) VALUES (5, 'Juan', 'juan@example.com', 'juan123', '696718256', 'C/', 'CUSTOMER');


-- Products
INSERT INTO products (id, name, price, description, stock, style,category_id) VALUES (1, 'Lampara', 30.00, 'Lampara de techo moderna en color azul', 78, 'Moderno',1);
INSERT INTO products (id, name, price, description, stock, style,category_id) VALUES (2, 'Armario', 200.00, 'Armario de madera', 20, 'Vintage',2);
INSERT INTO products (id, name, price, description, stock, style,category_id) VALUES (3, 'Cuadro', 60.00, 'Cuadro de arte de Londre', 50, 'Minimalista',3);
INSERT INTO products (id, name, price, description, stock, style,category_id) VALUES (4, 'Manta', 20.00, 'Manta de algodón', 30, 'Contemporaneo',4);
INSERT INTO products (id, name, price, description, stock, style,category_id) VALUES (5, 'Cajonera', 25.00, 'Cajonera de madera', 90, 'Anticuado',5);

-- Carts
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (1, 2, 285.00, 285.00, '2023-10-26', 'ACTIVE');
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (2, 1, 600.00, 600.00, '2024-06-12', 'ACTIVE');
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (3, 5, 400.00, 400.00, '2024-04-25', 'ACTIVE');
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (4, 3, 50.00, 50.00, '2025-02-24', 'ACTIVE');
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (5, 4, 80.00, 80.00, '2022-01-30', 'ACTIVE');

-- Cart Products
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (1, 1, 2, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (2, 1, 3, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (3, 2, 1, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (4, 2, 4, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (5, 3, 5, 1);

-- Token
INSERT INTO sesions (token, idUser, fecha) VALUES ('token1', 1, '2023-10-26');
INSERT INTO sesions (token, idUser, fecha) VALUES ('token2', 2, '2024-06-12');
INSERT INTO sesions (token, idUser, fecha) VALUES ('token3', 5, '2024-04-25');
INSERT INTO sesions (token, idUser, fecha) VALUES ('token4', 3, '2025-02-24');
INSERT INTO sesions (token, idUser, fecha) VALUES ('token5', 4, '2022-01-30');


