-- Product Assignments

-- 1. Lampara techo -> Salón (4) (Original)
INSERT INTO product_rooms (product_id, room_id) VALUES (1, 4);

-- 2. Difusores -> Salón (4), Baño (3)
INSERT INTO product_rooms (product_id, room_id) VALUES (2, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (2, 3);

-- 3. Incienso -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (3, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (3, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (3, 2);

-- 4. Ambientador electrico -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (4, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (4, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (4, 2);

-- 5. Spray -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (5, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (5, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (5, 2);

-- 6. Bolsitas -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (6, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (6, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (6, 2);

-- 7. Aceites -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (7, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (7, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (7, 2);

-- 8. Gel aromatico -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (8, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (8, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (8, 2);

-- 9. Sahumerio -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (9, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (9, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (9, 2);

-- 10. Atomizador de aromas -> Salón (4), Baño (3), Dormitorio (2)
INSERT INTO product_rooms (product_id, room_id) VALUES (10, 4);
INSERT INTO product_rooms (product_id, room_id) VALUES (10, 3);
INSERT INTO product_rooms (product_id, room_id) VALUES (10, 2);

-- 11. Jarrones modernos -> Salón (4) (Original)
INSERT INTO product_rooms (product_id, room_id) VALUES (11, 4);
-- 12. Funda de edredón -> Dormitorio (2) (Original)
INSERT INTO product_rooms (product_id, room_id) VALUES (12, 2);
-- 13. Organizador de joyas -> Dormitorio (2) (Original)
INSERT INTO product_rooms (product_id, room_id) VALUES (13, 2);
-- 14. Lámpara de pie -> Salón (4) (Original)
INSERT INTO product_rooms (product_id, room_id) VALUES (14, 4);
-- 15. Mueble para TV -> Salón (4) (Original)
INSERT INTO product_rooms (product_id, room_id) VALUES (15, 4);

-- Productos Cocina (16-94) -> Room 1
INSERT INTO product_rooms (product_id, room_id) 
SELECT id, 1 FROM products WHERE id BETWEEN 16 AND 94;

-- Productos Dormitorio (95-160) -> Room 2
INSERT INTO product_rooms (product_id, room_id) 
SELECT id, 2 FROM products WHERE id BETWEEN 95 AND 160;

-- Productos Baño (161-231) -> Room 3
INSERT INTO product_rooms (product_id, room_id) 
SELECT id, 3 FROM products WHERE id BETWEEN 161 AND 231;

-- Productos Salón (232-292) -> Room 4
INSERT INTO product_rooms (product_id, room_id) 
SELECT id, 4 FROM products WHERE id BETWEEN 232 AND 292;
