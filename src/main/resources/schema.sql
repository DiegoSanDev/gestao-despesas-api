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
	amount DECIMAL(10, 2) NOT NULL,
	description VARCHAR(150),
	is_parceled CHAR(1) DEFAULT 'N',
	start_date DATE NULL,
    end_date DATE NULL,
	created_at TIMESTAMP NOT NULL,
	update_at TIMESTAMP NULL,
	FOREIGN KEY (category_id) REFERENCES expense_category(id),
	FOREIGN KEY (payment_method_id) REFERENCES payment_method(id)
);

CREATE TABLE IF NOT EXISTS expense_parcel
(
	expense_id INTEGER NOT NULL,
	to_pay CHAR(1) DEFAULT 'N',
	amount DECIMAL(10, 2) NOT NULL,
	FOREIGN KEY (expense_id) REFERENCES expenses(id)
);