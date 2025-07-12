--Attribute Group
INSERT INTO product_attribute_group (id, name, created_at, created_by, updated_at, updated_by)
VALUES (396556939800219647, 'General', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh',
        CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh'),
       (396556939800219648, 'Book', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
        'thanh'),
       (396556939800219649, 'Something', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh',
        CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh'),
       (396556939800219650, 'Screen', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh',
        CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh'),
       (396556939800219651, 'Connectivity', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh',
        CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh'),
       (396556939800219652, 'Camera', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh',
        CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
        'thanh') (396556939800219653, 'Memory', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh');

--Attribute
INSERT INTO product_attribute (id, name, created_at, created_by, updated_at, updated_by)
VALUES (16556939800219641, 'Manufacture', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', null, null),
       (16556939800219642, 'Made in', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', null, null),
       (16556939800219643, 'Material', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', null, null),
       (16556939800219644, 'Author', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', null, null);

--Product Template
INSERT INTO product_template (id, name, created_at, created_by, updated_at, updated_by)
VALUES (1, 'Sample Template', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 'thanh', CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
        'thanh');

--Product Attribute template
INSERT INTO product_attribute_template (id, product_attribute, product_template, display_order)
VALUES (486580923245727745, 486580923245727745, 1, 0),
       (486580923245727746, 486580923245727746, 1, 1),
       (486580923245727747, 486580923245727747, 1, 2),
       (486580923245727748, 486580923245727748, 1, 3);

--Product
INSERT INTO product(id, name, slug, has_variant, sku, gtin, price, parent_id, thumbnail, brand, supplier, description,
                    status)
VALUES ('74f9341a-54c1-4453-84e4-147e656e4a9c', 'Nhà Giả Kim', 'nha-gia-kim', true, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('366f785f-26dd-4c33-8452-0b172ef0a5de', 'Iphone 16 Pro Max', 'iphone-16-pro-max', true, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('14e9a579-d824-4386-9d3c-74b53ef2a626', 'Iphone 15 Pro Max', 'iphone-15-pro-max', true, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('a588ffe9-1b22-4f37-ae58-553a6ae93677', 'Iphone 14 Pro Max', 'iphone-14-pro-max', true, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('d0dc832d-68ea-4ab6-b078-940869ec4f9e', 'Iphone 16 Pro', 'iphone-16-pro', true, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('61f81838-50c5-4f5f-9efc-9473d32f4f1c', 'Iphone 15 Pro', 'iphone-15-pro', true, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('5388efb4-d56d-4eb4-83a6-cbf7fa404ec6', 'Iphone 14 Pro', 'iphone-14-pro', true, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('66fe7e60-82a1-44e7-9eaa-b46d3c03386b', 'Những Người Khốn Khổ', 'nhung-nguoi-khon-kho', true, null, null, null,
        null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('ce143f11-fe72-4909-8919-5665c4bbd91a', 'Sapiens Lược Sử Loài Người', 'sapiens-luoc-su-loai-nguoi', false, null,
        null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('9007080f-51a7-45e8-91c9-d82e6943200a', 'Guns, Germs, and Steel: The Fates of Human Societies',
        'guns-germs-and-steel', false, null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('8e97edd5-ce80-4ff9-8956-ba0c497ac8f7', 'Samsung Galaxy Fold 6', 'samsung-galaxy-fold-6', true, null, null,
        null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('26c1974d-2b14-4ec9-a36e-ed8676a252dc', 'Samsung Galaxy Zflip 6', 'samsung-galaxy-zflip-6', true, null, null,
        null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('e6f7a4c2-5d11-47c0-8623-89694e2e5977', 'Samsung Galaxy S25 Ultra', 'samsung-galaxy-s25-ultra', true, null,
        null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('4e528f17-c033-4fd0-8fbe-9ec6da03fe36', 'Patek Philippe Nautilus 5723', 'patek-philippe-nautilus-5723', false,
        null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('eb553ba5-475d-4335-9d69-9bf3da7ee70d', 'Rolex GMT Master II ', 'rolex-gmt-master-ii', false, null, null, null,
        null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE'),
       ('933585e6-4983-48c6-b4fa-160db579baf5', 'Richard Mille RM 70-01 Tourbillon', 'richard-mille-rm-70-01', false,
        null, null, null, null,
        'https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg',
        null, null, 'description', 'ACTIVE');


--Product Variant
INSERT INTO product (id, name, has_variant, parent_id, sku, gtin, price, thumbnail, status, description)
VALUES ('d1a06c9a-78cb-4c7e-beef-0fb4245aacad', 'Iphone 16 Pro Max Đen 128GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-1432', '1845678901901', 25000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('722b82e5-04c2-44ca-9042-55f74460687b', 'Iphone 16 Pro Max Đen 256GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-5432', '1845678901001', 35000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('86be8381-04fa-42d6-a9d7-6349b482f00d', 'Iphone 16 Pro Max Đen 512GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-7652', '1845678901201', 45000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('d072fe63-af8a-4de0-9e61-9a897149332a', 'Iphone 16 Pro Max Đen 1TB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-1245', '1845678901301', 55000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('bb2e3911-e3ad-4689-a1af-2fedbe58b11e', 'Iphone 16 Pro Max Trắng 128GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-6565', '1845678901021', 26000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('c86cc7d9-a0bf-43e1-9943-8b70639dda1e', 'Iphone 16 Pro Max Trắng 256GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-8623', '1845678901401', 36000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('e50e7957-f71d-4787-ad60-9fcba599debd', 'Iphone 16 Pro Max Trắng 512GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-4184', '1845678901501', 46000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('4d7a3609-bf78-4e4d-97a6-b392b5b986d0', 'Iphone 16 Pro Max Trắng 1TB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-1457', '1845678901601', 56000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('94e86ccf-c4ff-41a7-a60b-289eb726f8c0', 'Iphone 16 Pro Max Titan 128GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-6894', '1845678901071', 29000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('6d3b283e-00c9-46a2-93a3-443695a87e53', 'Iphone 16 Pro Max Titan 256GB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-9867', '1845678901611', 39000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('f676813b-09cc-42cb-916b-6d4777e76ada', 'Iphone 16 Pro Max Titan 512B', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-6474', '1845678901614', 49000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description'),
       ('ab25dead-5017-4cbb-9cdb-7bda81f45eaa', 'Iphone 16 Pro Max Titan 1TB', false,
        '366f785f-26dd-4c33-8452-0b172ef0a5de', 'IPHON-COLOR-CAPAC-8735', '1845678901612', 59000000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFTtvUMmUEQhDJHf4wYNhUz3JaUhUjBnznkzsYoc-ik444TcrTrd_dN8ehrlmJwO8e0y8&usqp=CAU',
        'ACTIVE', 'description');


--Product Attribute Value
INSERT INTO product_attribute_value (id, value, product_id, product_attribute_id)
VALUES (486580923245727745, 'Paulo Coelho', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727745),
       (486580923245727746, '225', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727746),
       (486580923245727747, '978-604-53-7204-3', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727747),
       (486580923245727748, 'Potugal', '74f9341a-54c1-4453-84e4-147e656e4a9c', 486580923245727748);


--Variant Attribute
INSERT INTO variant_attribute (id, name)
VALUES (1865809232457270, 'Size'),
       (1865809232457271, 'Color'),
       (1865809232457272, 'Capacity');

INSERT INTO variant_attribute_option (id, variant_id, attribute_id, value, display_order)
VALUES (1965809232457270, 'd1a06c9a-78cb-4c7e-beef-0fb4245aacad', 1865809232457271, 'Black', 1),
       (1965809232457271, '722b82e5-04c2-44ca-9042-55f74460687b', 1865809232457271, 'White', 2),
       (1965809232457272, '722b82e5-04c2-44ca-9042-55f74460687b', 1865809232457271, 'Titan', 3),
       (1965809232457273, 'd1a06c9a-78cb-4c7e-beef-0fb4245aacad', 1865809232457272, '128 GB', 1),
       (1965809232457274, '', 1865809232457272, '256 GB', 2),
       (1965809232457275, '', 1865809232457272, '512 GB', 3),
       (1965809232457276, '', 1865809232457272, '1 TB', 4);

INSERT INTO variant_option_combination (id, product_id, variant_attribute_id, display_order)
VALUES (556939800219647, '366f785f-26dd-4c33-8452-0b172ef0a5de', 1865809232457271, 1),
       (556939800219648, '366f785f-26dd-4c33-8452-0b172ef0a5de', 1865809232457272, 2);


INSERT INTO product_category (id, product_id, category_id, display_order)
VALUES (1, '366f785f-26dd-4c33-8452-0b172ef0a5de', '44c8112a-4794-405b-91ec-98ca64296825', 1),
       (2, '366f785f-26dd-4c33-8452-0b172ef0a5de', '004f0f2b-3d9c-4cbf-95d6-b2852dfe5468', 2),
       (3, '366f785f-26dd-4c33-8452-0b172ef0a5de', '00f494ad-3f51-4a10-bb16-51f137a9299b', 3);