-- Categories
INSERT INTO categories (id,name) VALUES (1, 'Iluminacion');
INSERT INTO categories (id,name) VALUES (2, 'Muebles');
INSERT INTO categories (id,name) VALUES (3, 'Decoracion');
INSERT INTO categories (id,name) VALUES (4, 'Textiles');
INSERT INTO categories (id,name) VALUES (5, 'Organizadores');

-- Users
-- Alfonso (alfonso123 -> Plain Text)
INSERT INTO users (id, name, email, password, phone, address, role) VALUES ('11111111-1111-1111-1111-111111111111', 'Alfonso', 'alfonso@example.com', 'alfonso123', '696781526', 'C/', 'ADMIN');
-- Marta (marta123 -> Plain Text)
INSERT INTO users (id, name, email, password, phone, address, role) VALUES ('22222222-2222-2222-2222-222222222222', 'Marta', 'marta@example.com', 'marta123', '694518796', 'C/', 'CUSTOMER');
-- Ignacio (ignacio123 -> Plain Text)
INSERT INTO users (id, name, email, password, phone, address, role) VALUES ('33333333-3333-3333-3333-333333333333', 'Ignacio', 'ignacio@example.com', 'ignacio123', '696718256', 'C/', 'CUSTOMER');
-- Sonia (sonia123 -> Plain Text)
INSERT INTO users (id, name, email, password, phone, address, role) VALUES ('44444444-4444-4444-4444-444444444444', 'Sonia', 'sonia@example.com', 'sonia123', '697814256', 'C/', 'ADMIN');
-- Juan (juan123 -> Plain Text)
INSERT INTO users (id, name, email, password, phone, address, role) VALUES ('55555555-5555-5555-5555-555555555555', 'Juan', 'juan@example.com', 'juan123', '696718256', 'C/', 'CUSTOMER');


-- Products
INSERT INTO products (id, name, price, description, stock, style, image, category_id) VALUES (1, 'Lampara techo', 30.00, 'Lampara de techo moderna en colores azul y marron', 78, 'Moderno', '1.jpg', 1);
INSERT INTO products (id, name, price, description, stock, style, image, category_id) VALUES (2, 'Armario vintage', 200.00, 'Armario de madera de estilo vintage', 20, 'Vintage', '2.jpg', 2);
INSERT INTO products (id, name, price, description, stock, style, image, category_id) VALUES (3, 'Cuadro Londres', 60.00, 'Cuadro de arte de Londres', 50, 'Minimalista', '3.jpg', 3);
INSERT INTO products (id, name, price, description, stock, style, image, category_id) VALUES (4, 'Manta algodón', 20.00, 'Manta de algodón colores marron y azul', 30, 'Contemporaneo', '4.jpg', 4);
INSERT INTO products (id, name, price, description, stock, style, image, category_id) VALUES (5, 'Cajonera anticuada', 25.00, 'Cajonera de madera anticuada', 90, 'Anticuado', '5.jpg', 5);

-- Carts
-- User 2 -> Marta
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (1, '22222222-2222-2222-2222-222222222222', 285.00, 285.00, '2023-10-26', 'ACTIVE');
-- User 1 -> Alfonso
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (2, '11111111-1111-1111-1111-111111111111', 600.00, 600.00, '2024-06-12', 'ACTIVE');
-- User 5 -> Juan
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (3, '55555555-5555-5555-5555-555555555555', 400.00, 400.00, '2024-04-25', 'ACTIVE');
-- User 3 -> Ignacio
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (4, '33333333-3333-3333-3333-333333333333', 50.00, 50.00, '2025-02-24', 'ACTIVE');
-- User 4 -> Sonia
INSERT INTO carts (id, user_id, total, price, date_cart, status) VALUES (5, '44444444-4444-4444-4444-444444444444', 80.00, 80.00, '2022-01-30', 'ACTIVE');

-- Cart Products
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (1, 1, 2, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (2, 1, 3, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (3, 2, 1, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (4, 2, 4, 1);
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (5, 3, 5, 1);

-- Token
-- User 1 -> Alfonso
INSERT INTO sesions (id, token_value, user_id, created_at) VALUES ('66666666-6666-6666-6666-666666666666', 'token1', '11111111-1111-1111-1111-111111111111', '2023-10-26 10:00:00');
-- User 2 -> Marta
INSERT INTO sesions (id, token_value, user_id, created_at) VALUES ('77777777-7777-7777-7777-777777777777', 'token2', '22222222-2222-2222-2222-222222222222', '2024-06-12 10:00:00');
-- User 5 -> Juan
INSERT INTO sesions (id, token_value, user_id, created_at) VALUES ('88888888-8888-8888-8888-888888888888', 'token3', '55555555-5555-5555-5555-555555555555', '2024-04-25 10:00:00');
-- User 3 -> Ignacio
INSERT INTO sesions (id, token_value, user_id, created_at) VALUES ('99999999-9999-9999-9999-999999999999', 'token4', '33333333-3333-3333-3333-333333333333', '2025-02-24 10:00:00');
-- User 4 -> Sonia
INSERT INTO sesions (id, token_value, user_id, created_at) VALUES ('00000000-0000-0000-0000-000000000000', 'token5', '44444444-4444-4444-4444-444444444444', '2022-01-30 10:00:00');
