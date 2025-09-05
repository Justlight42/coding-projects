-- database sub_reminder_data
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users, subscriptions, reminders CASCADE;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	email varchar(255) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE subscriptions (
    sub_id SERIAL,
    user_id int NOT NULL,
    sub_name varchar NOT NULL,
    cost decimal(10,2) NOT NULL,
    billing_cycle varchar NOT NULL, -- "monthly" or "yearly"
    next_billing_date date NOT NULL,
    site_url varchar,
    CONSTRAINT PK_sub PRIMARY KEY (sub_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE reminders (
    reminder_id SERIAL,
    sub_id int NOT NULL,
    reminder_date date NOT NULL,
    sent boolean DEFAULT FALSE,
    CONSTRAINT PK_reminder PRIMARY KEY (reminder_id),
    CONSTRAINT FK_sub_id FOREIGN KEY (sub_id) REFERENCES subscriptions(sub_id) ON DELETE CASCADE
);

COMMIT TRANSACTION;
