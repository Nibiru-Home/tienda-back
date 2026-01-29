CREATE TABLE rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE product_rooms (
    product_id INT NOT NULL,
    room_id INT NOT NULL,
    PRIMARY KEY (product_id, room_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

INSERT INTO rooms (id, name) VALUES (1, 'Cocina');
INSERT INTO rooms (id, name) VALUES (2, 'Dormitorio');
INSERT INTO rooms (id, name) VALUES (3, 'Baño');
INSERT INTO rooms (id, name) VALUES (4, 'Salón');
