--Attribute Group
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219647, 'General', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219648, 'Book', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219649, 'Something', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219650, 'Screen', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219651, 'Connectivity', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219652, 'Camera', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by) VALUES(396556939800219653, 'Memory', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');

--Attribute
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(486580923245727745,'Author', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(486580923245727746,'Pages', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(486580923245727747,'ISBN', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');
INSERT INTO product_attribute(id, name, product_attribute_group, created_at, created_by, updated_at, updated_by) VALUES(486580923245727748,'Language', 396556939800219648, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');

--Product Template
INSERT INTO product_template (id, name, created_at, created_by, updated_at, updated_by) VALUES (1, 'Sample Template', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');

--Product Attribute template
INSERT INTO product_attribute_template (id, product_attribute, product_template, display_order) VALUES(486580923245727745, 486580923245727745, 1, 0);
INSERT INTO product_attribute_template (id, product_attribute, product_template, display_order) VALUES(486580923245727746, 486580923245727746, 1, 1);
INSERT INTO product_attribute_template (id, product_attribute, product_template, display_order) VALUES(486580923245727747, 486580923245727747, 1, 2);
INSERT INTO product_attribute_template (id, product_attribute, product_template, display_order) VALUES(486580923245727748, 486580923245727748, 1, 3);

--Product
INSERT INTO product(id, name, slug, category, brand, supplier, description, status) VALUES('74f9341a-54c1-4453-84e4-147e656e4a9c', 'Nhà Giả Kim', 'nha-gia-kim', 'd8be3328-2999-4557-83d7-fe939776f12f', null, null, 'description', 'ACTIVE');

--Product Attribute
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727745, 'Paulo Coelho', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727745);
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727746, '225', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727746);
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727747, '978-604-53-7204-3', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727747);
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727748,'Potugal', '74f9341a-54c1-4453-84e4-147e656e4a9c',486580923245727748);
