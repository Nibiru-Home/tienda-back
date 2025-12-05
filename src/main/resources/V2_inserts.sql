-- Categories
INSERT INTO category (id,name) VALUES (1, 'Iluminacion');
INSERT INTO category (id,name) VALUES (2, 'Muebles');
INSERT INTO category (id,name) VALUES (3, 'Decoracion');
INSERT INTO category (id,name) VALUES (4, 'Textiles');
INSERT INTO category (id,name) VALUES (5, 'Organizadores');

-- Users
INSERT INTO user (id, name, email, password, phone, address, role) VALUES (1, 'Alfonso', 'alfonso@example.com', 'alfonso123', '696781526', 'C/', 'ADMIN');
INSERT INTO user (id, name, email, password, phone, address, role) VALUES (2, 'Marta', 'marta@example.com', 'marta123', '694518796', 'C/', 'CUSTOMER');
INSERT INTO user (id, name, email, password, phone, address, role) VALUES (3, 'Ignacio', 'ignacio@example.com', 'ignacio123', '696718256', 'C/', 'CUSTOMER');
INSERT INTO user (id, name, email, password, phone, address, role) VALUES (4, 'Sonia', 'sonia@example.com', 'sonia123', '697814256', 'C/', 'ADMIN');
INSERT INTO user (id, name, email, password, phone, address, role) VALUES (5, 'Juan', 'juan@example.com', 'juan123', '696718256', 'C/', 'CUSTOMER');


-- Products
INSERT INTO product (id, name, price, description, stock, category_id) VALUES (1, 'Lampara ', 1200.00, 'High performance laptop', 10, 1);
INSERT INTO product (id, name, price, description, stock, category_id) VALUES (2, 'Smartphone', 800.00, 'Latest model', 20, 1);
INSERT INTO product (id, name, price, description, stock, category_id) VALUES (3, 'Novel', 15.00, 'Bestselling novel', 50, 2);
INSERT INTO product (id, name, price, description, stock, category_id) VALUES (4, 'Textbook', 50.00, 'Educational textbook', 30, 2);

-- Carts
INSERT INTO cart (id, user_id, total, price, date_cart, status) VALUES (1, 2, 815.00, 815.00, '2023-10-26', 'ACTIVE');

-- Cart Products
INSERT INTO cart_product (id, cart_id, product_id, quantity) VALUES (1, 1, 2, 1);
INSERT INTO cart_product (id, cart_id, product_id, quantity) VALUES (2, 1, 3, 1);
