BEGIN TRANSACTION;

INSERT INTO users (username, password_hash, email, role)
VALUES
    ('user1', 'hashedpassword1', 'user1@example.com', 'ROLE_USER'),
    ('user2', 'hashedpassword2', 'user2@example.com', 'ROLE_USER'),
    ('user3', 'hashedpassword3', 'user3@example.com', 'ROLE_USER'),
    ('user4', 'hashedpassword4', 'user4@example.com', 'ROLE_USER'),
    ('user5', 'hashedpassword5', 'user5@example.com', 'ROLE_USER'),
    ('user6', 'hashedpassword6', 'user6@example.com', 'ROLE_USER'),
    ('user7', 'hashedpassword7', 'user7@example.com', 'ROLE_USER'),
    ('user8', 'hashedpassword8', 'user8@example.com', 'ROLE_USER'),
    ('user9', 'hashedpassword9', 'user9@example.com', 'ROLE_USER');

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
