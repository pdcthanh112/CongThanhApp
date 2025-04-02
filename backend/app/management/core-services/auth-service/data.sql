-- Account
INSERT INTO accounts(id, account_id, emp_email, password_hash, status) VALUES(1, 'thanhpdc', 'thanhpdc@pdcthanh.com', '$2b$10$nYQjXKc5TthXL87DbLF04.vZqpquLW4vmvcXR5mMszi8HD01NXS2O', 'ACTIVE')

-- Role
INSERT INTO roles(id, name, created_at, created_by, updated_at, updated_by) VALUES(196556939800219641, 'Super Admin', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219642, 'Admin', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219643, 'CEO', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219644, 'CTO', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219645, 'CFO', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219646, 'COO', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219647, 'CHRO', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219648, 'CMO', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219649, 'HR Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219650, 'Finance Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219651, 'IT Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219652, 'Marketing Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219653, 'Sales Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219654, 'Product Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219655, 'Project Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219656, 'Operations Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219657, 'Legal Manager', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219658, 'HR Staff', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219659, 'Finance Staff', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219660, 'IT Support', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219661, 'Sales Executive', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219662, 'Marketing Specialist', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219663, 'Customer Support ', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              (196556939800219664, 'Legal Specialist', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh'),
	                                                                              

-- Permission
INSERT INTO permisstion (id, action, resource, created_at, created_by, updated_at, updated_by) VALUES (196556939800219001, 'view', 'list_employee', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219003, 'create', 'employee_account', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219004, 'update', 'employee_account', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219005, 'disable', 'employee_account', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219006, 'approve', 'recruitment', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219007, 'view', 'payroll', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219008, 'view', 'financial_reports', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219009, 'approve', 'expenses', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219010, 'view', 'invoices', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219011, 'update', 'expenses', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219012, 'view', 'campaigns', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219013, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219014, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219015, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219016, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219017, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219018, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219019, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219020, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219021, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219022, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219023, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219024, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219025, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219026, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219027, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219028, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219029, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219030, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219031, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219032, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')
                                                                                                    
                                                                                                      (196556939800219002, 'view', 'employee_detail', CURRENT_TIMESTAMP, 'thanh', CURRENT_TIMESTAMP, 'thanh')