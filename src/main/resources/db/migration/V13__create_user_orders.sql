CREATE TABLE user_orders (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    total DOUBLE NOT NULL,
    date DATETIME NOT NULL,
    status VARCHAR(255) NOT NULL,
    cart_id INT NOT NULL,
    CONSTRAINT fk_user_orders_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_orders_cart FOREIGN KEY (cart_id) REFERENCES carts(id)
);
