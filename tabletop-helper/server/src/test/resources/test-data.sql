BEGIN TRANSACTION;

INSERT INTO users (username, password_hash, role)
VALUES
    ('JustinG', 'hashedpassword1', 'ROLE_ADMIN'),
    ('PlayerOne', 'hashedpassword2', 'ROLE_USER'),
    ('GamerX', 'hashedpassword3', 'ROLE_USER'),
    ('ShadowHunter', 'hashedpassword4', 'ROLE_USER'),
    ('Speedster', 'hashedpassword5', 'ROLE_USER'),
    ('StealthNinja', 'hashedpassword6', 'ROLE_USER'),
    ('CyberWarrior', 'hashedpassword7', 'ROLE_USER'),
    ('EliteSniper', 'hashedpassword8', 'ROLE_USER'),
    ('HyperSpeed', 'hashedpassword9', 'ROLE_USER'),
    ('TitanShield', 'hashedpassword10', 'ROLE_USER');

INSERT INTO game_mode (mode_name)
VALUES
    ('Score Mode'),
    ('Health Mode'),
    ('Hybrid Mode');

INSERT INTO sessions (mode_id, created_by_user_id, end_time, timer_duration)
VALUES
    (1, 1, '2025-06-07 18:30:00', 60),
    (2, 2, '2025-06-07 19:00:00', 45),
    (3, 3, NULL, 30), -- ongoing session
    (1, 4, '2025-06-07 20:30:00', 55),
    (2, 5, NULL, 50); -- ongoing session

INSERT INTO team (session_id, team_name)
VALUES
    (1, 'Red Warriors'),
    (1, 'Blue Strikers'),
    (2, 'Shadow Masters'),
    (3, 'Storm Guardians'),
    (4, 'Inferno Hawks');

INSERT INTO player (team_id, user_id, name, health, score)
VALUES
    (1, 1, 'JustinG', 100, 0),
    (1, 2, 'PlayerOne', 90, 10),
    (2, 3, 'GamerX', 95, 20),
    (3, 4, 'ShadowHunter', 85, 15),
    (4, 5, 'Speedster', 80, 30);

INSERT INTO player_action (session_id, player_id, action_type, amount)
VALUES
    (1, 1, 'score', 10),
    (1, 2, 'health', -5),
    (2, 3, 'score', 20),
    (3, 4, 'health', -10),
    (4, 5, 'score', 25);

INSERT INTO user_session (user_id, session_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4);

COMMIT TRANSACTION;
