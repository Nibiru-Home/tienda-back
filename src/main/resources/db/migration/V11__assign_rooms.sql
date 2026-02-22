INSERT INTO product_rooms (product_id, room_id) VALUES (1, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (2, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (2, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (3, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (3, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (3, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (4, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (4, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (4, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (5, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (5, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (5, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (6, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (6, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (6, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (7, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (7, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (7, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (8, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (8, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (8, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (9, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (9, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (9, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (10, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (10, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (10, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (11, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (12, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (13, 2);
INSERT INTO product_rooms (product_id, room_id) VALUES (14, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (15, 4);
INSERT INTO product_rooms (product_id, room_id)
SELECT id, 1 FROM products WHERE id BETWEEN 16 AND 94;
INSERT INTO product_rooms (product_id, room_id)
SELECT id, 2 FROM products WHERE id BETWEEN 95 AND 160;
INSERT INTO product_rooms (product_id, room_id)
SELECT id, 3 FROM products WHERE id BETWEEN 161 AND 231;
INSERT INTO product_rooms (product_id, room_id)
SELECT id, 4 FROM products WHERE id BETWEEN 232 AND 292;
