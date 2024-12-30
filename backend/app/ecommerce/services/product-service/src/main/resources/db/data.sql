-----------------------------------------------ATTRIBUTE
--Attribute Group
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219648, 'Book', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');

--Attribute
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(396580923245727745,'Author', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(396580923245727746,'Pages', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(396580923245727747,'ISBN', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(396580923245727748,'Language', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');

--Product
INSERT INTO product(id, name, slug, category, brand, supplier, description, status) VALUES('74f9341a-54c1-4453-84e4-147e656e4a9c', 'Nhà Giả Kim', 'nha-gia-kim', 'd8be3328-2999-4557-83d7-fe939776f12f', null, null, 'description', 'ACTIVE');
