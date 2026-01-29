-- Reuse existing product images (1..15) for all products
DELETE FROM product_images;

UPDATE products
SET image = CONCAT(MOD(id - 1, 15) + 1, '.jpg');

INSERT INTO product_images (product_id, image_url)
SELECT id, CONCAT(MOD(id - 1, 15) + 1, '.jpg')
FROM products;

INSERT INTO product_images (product_id, image_url)
SELECT id, CONCAT(MOD(id - 1, 15) + 1, '.1.png')
FROM products;

INSERT INTO product_images (product_id, image_url)
SELECT id, CONCAT(MOD(id - 1, 15) + 1, '.2.png')
FROM products;
