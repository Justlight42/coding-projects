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
    reminder_date date,
    sent boolean DEFAULT FALSE,
    CONSTRAINT PK_reminder PRIMARY KEY (reminder_id),
    CONSTRAINT FK_sub_id FOREIGN KEY (sub_id) REFERENCES subscriptions(sub_id) ON DELETE CASCADE
);

-- *************************************************************************************************
-- Sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO users (username, password_hash, email, role)
VALUES
    ('admin', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'justintruong42@gmail.com', 'ROLE_ADMIN'),
    ('user1', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user1@example.com', 'ROLE_USER'),
    ('user2', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user2@example.com', 'ROLE_USER'),
    ('user3', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user3@example.com', 'ROLE_USER'),
    ('user4', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user4@example.com', 'ROLE_USER'),
    ('user5', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user5@example.com', 'ROLE_USER'),
    ('user6', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user6@example.com', 'ROLE_USER'),
    ('user7', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user7@example.com', 'ROLE_USER'),
    ('user8', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user8@example.com', 'ROLE_USER'),
    ('user9', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'user9@example.com', 'ROLE_USER');

-- Insert statements for subscriptions table
INSERT INTO subscriptions (user_id, sub_name, cost, billing_cycle, next_billing_date, site_url)
VALUES
    (1, 'Netflix', 15.99, 'monthly', '2025-09-15', 'https://www.netflix.com'),
    (1, 'Spotify Premium', 9.99, 'monthly', '2025-09-10', 'https://www.spotify.com'),
    (2, 'Adobe Creative Cloud', 52.99, 'monthly', '2025-09-20', 'https://www.adobe.com'),
    (2, 'Amazon Prime', 139.00, 'yearly', '2026-01-05', 'https://www.amazon.com/prime'),
    (3, 'Notion Pro', 8.00, 'monthly', '2025-09-12', 'https://www.notion.so'),
    (3, 'GitHub Copilot', 10.00, 'monthly', '2025-09-18', 'https://github.com/features/copilot');

-- Insert statements for reminders table
INSERT INTO reminders (sub_id, reminder_date, sent)
VALUES
    (1, '2025-09-13', FALSE),  -- Netflix reminder 2 days before billing
    (2, '2025-09-08', TRUE),   -- Spotify reminder already sent
    (3, '2025-09-18', FALSE),  -- Adobe reminder 2 days before billing
    (4, '2025-12-30', FALSE),  -- Amazon Prime yearly reminder
    (5, '2025-09-10', TRUE),   -- Notion reminder already sent
    (6, '2025-09-16', FALSE);  -- GitHub Copilot reminder upcoming


COMMIT TRANSACTION;
