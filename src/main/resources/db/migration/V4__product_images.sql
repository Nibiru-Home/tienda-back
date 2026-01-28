CREATE TABLE product_images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

INSERT INTO product_images (product_id, image_url) VALUES (1, '1.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (1, '1.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (1, '1.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (2, '2.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (2, '2.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (2, '2.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (3, '3.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (3, '3.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (3, '3.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (4, '4.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (4, '4.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (4, '4.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (5, '5.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (5, '5.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (5, '5.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (6, '6.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (6, '6.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (6, '6.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (7, '7.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (7, '7.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (7, '7.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (8, '8.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (8, '8.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (8, '8.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (9, '9.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (9, '9.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (9, '9.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (10, '10.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (10, '10.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (10, '10.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (11, '11.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (11, '11.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (11, '11.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (12, '12.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (12, '12.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (12, '12.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (13, '13.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (13, '13.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (13, '13.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (14, '14.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (14, '14.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (14, '14.2.png');

INSERT INTO product_images (product_id, image_url) VALUES (15, '15.jpg');
INSERT INTO product_images (product_id, image_url) VALUES (15, '15.1.png');
INSERT INTO product_images (product_id, image_url) VALUES (15, '15.2.png');
