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
INSERT INTO product(id, name, slug, category, brand, supplier, description, status) VALUES('74f9341a-54c1-4453-84e4-147e656e4a9c', 'Nhà Giả Kim', 'nha-gia-kim', 'd8be3328-2999-4557-83d7-fe939776f12f', null, null, 'description', 'ACTIVE'),
                                                                                          ('366f785f-26dd-4c33-8452-0b172ef0a5de', 'Iphone 16 Pro Max', 'iphone-16-pro-max', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('14e9a579-d824-4386-9d3c-74b53ef2a626', 'Iphone 15 Pro Max', 'iphone-15-pro-max', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('a588ffe9-1b22-4f37-ae58-553a6ae93677', 'Iphone 14 Pro Max', 'iphone-14-pro-max', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('d0dc832d-68ea-4ab6-b078-940869ec4f9e', 'Iphone 16 Pro', 'iphone-16-pro', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('61f81838-50c5-4f5f-9efc-9473d32f4f1c', 'Iphone 15 Pro', 'iphone-15-pro', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('5388efb4-d56d-4eb4-83a6-cbf7fa404ec6', 'Iphone 14 Pro', 'iphone-14-pro', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('66fe7e60-82a1-44e7-9eaa-b46d3c03386b', 'Những Người Khốn Khổ', 'nhung-nguoi-khon-kho', 'd8be3328-2999-4557-83d7-fe939776f12f', null, null, 'description', 'ACTIVE'),
                                                                                          ('ce143f11-fe72-4909-8919-5665c4bbd91a', 'Sapiens Lược Sử Loài Người', 'sapiens-luoc-su-loai-nguoi', 'd8be3328-2999-4557-83d7-fe939776f12f', null, null, 'description', 'ACTIVE'),
                                                                                          ('9007080f-51a7-45e8-91c9-d82e6943200a', 'Guns, Germs, and Steel: The Fates of Human Societies', 'guns-germs-and-steel', 'd8be3328-2999-4557-83d7-fe939776f12f', null, null, 'description', 'ACTIVE'),
                                                                                          ('8e97edd5-ce80-4ff9-8956-ba0c497ac8f7', 'Samsung Galaxy Fold 6', 'samsung-galaxy-fold-6', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('26c1974d-2b14-4ec9-a36e-ed8676a252dc', 'Samsung Galaxy Zflip 6', 'samsung-galaxy-zflip-6', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('e6f7a4c2-5d11-47c0-8623-89694e2e5977', 'Samsung Galaxy S25 Ultra', 'samsung-galaxy-s25-ultra', '44c8112a-4794-405b-91ec-98ca64296825', null, null, 'description', 'ACTIVE'),
                                                                                          ('4e528f17-c033-4fd0-8fbe-9ec6da03fe36', 'Patek Philippe Nautilus 5723', 'patek-philippe-nautilus-5723', '414f47d5-b33d-4493-8449-4bbd8cb4f499', null, null, 'description', 'ACTIVE'),
                                                                                          ('eb553ba5-475d-4335-9d69-9bf3da7ee70d', 'Rolex GMT Master II ', 'rolex-gmt-master-ii', '414f47d5-b33d-4493-8449-4bbd8cb4f499', null, null, 'description', 'ACTIVE'),
                                                                                          ('933585e6-4983-48c6-b4fa-160db579baf5', 'Richard Mille RM 70-01 Tourbillon', 'richard-mille-rm-70-01', '414f47d5-b33d-4493-8449-4bbd8cb4f499', null, null, 'description', 'ACTIVE');

--Product Attribute
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727745, 'Paulo Coelho', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727745);
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727746, '225', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727746);
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727747, '978-604-53-7204-3', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727747);
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id) VALUES (486580923245727748,'Potugal', '74f9341a-54c1-4453-84e4-147e656e4a9c',486580923245727748);
