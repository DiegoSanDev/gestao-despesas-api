CREATE TABLE IF NOT EXISTS expense_category
(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	description VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment_method
(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	description VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS expense
(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	category_id INTEGER NOT NULL,
	payment_method_id INTEGER NOT NULL,
	is_paid CHAR(1) DEFAULT 'N',
	amount DECIMAL(10, 2) NOT NULL,
	description VARCHAR(150),
	expense_date DATE NOT NULL,
	created_at TIMESTAMP NOT NULL,
	update_at TIMESTAMP NULL,
	FOREIGN KEY (category_id) REFERENCES expense_category(id),
	FOREIGN KEY (payment_method_id) REFERENCES payment_method(id)
);

CREATE TABLE IF NOT EXISTS expense_parcel
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
	expense_id INTEGER NOT NULL,
	quantity INTEGER NOT NULL,
	start_date DATE NOT NULL,
	FOREIGN KEY (expense_id) REFERENCES expense(id)
);

CREATE TABLE IF NOT EXISTS parcel_control
(
	parcel_id INTEGER NOT NULL,
	protocol VARCHAR(36) NOT NULL,
	is_paid CHAR(1) DEFAULT 'N',
	amount DECIMAL(10, 2) NOT NULL,
	month_payment VARCHAR(20) NOT NULL,
	FOREIGN KEY (parcel_id) REFERENCES expense_parcel(id) ON DELETE CASCADE
);