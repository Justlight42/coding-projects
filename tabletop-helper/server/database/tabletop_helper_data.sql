-- database tabletop_helper_data
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users, player, sessions, player_action, team CASCADE;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

--sessions (name is pluralized because 'session' is a SQL keyword)
CREATE TABLE sessions (
    session_id SERIAL,
    game_mode int NOT NULL CHECK(game_mode IN (0, 1)),
    created_by_user_id int NOT NULL,
    start_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_time timestamp,
    session_duration varchar(20),
    CONSTRAINT PK_session PRIMARY KEY (session_id),
    CONSTRAINT FK_created FOREIGN KEY (created_by_user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE team (
    team_id SERIAL,
    session_id int NOT NULL,
    team_name varchar(155) NOT NULL,
    CONSTRAINT PK_team PRIMARY KEY (team_id),
    CONSTRAINT FK_session FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
    CONSTRAINT unique_team UNIQUE (session_id, team_name)
);

CREATE TABLE player (
    player_id SERIAL,
    session_id int NOT NULL,
    team_id int,
    user_id int,
    player_name varchar(100) NOT NULL,
    health int,
    score int DEFAULT 0,
    CONSTRAINT PK_player PRIMARY KEY (player_id),
    CONSTRAINT FK_session_player FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
    CONSTRAINT FK_team FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE SET NULL,
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE player_action (
    action_id SERIAL,
    session_id int NOT NULL,
    player_id int NOT NULL,
    action_type varchar(50) NOT NULL CHECK (action_type IN ('score','health')),
    amount int NOT NULL,
    action_time timestamp DEFAULT CURRENT_TIMESTAMP,
    action_reverted BOOLEAN DEFAULT FALSE,
    CONSTRAINT PK_action PRIMARY KEY (action_id),
    CONSTRAINT FK_session FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
    CONSTRAINT FK_player FOREIGN KEY (player_id) REFERENCES player(player_id) ON DELETE CASCADE
);


-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO
    users (username, password_hash, role)
VALUES
    ('admin', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN'),
    ('PlayerOne', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('GamerX', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('ShadowHunter', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('Speedster', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('StealthNinja', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
    ('CyberWarrior', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
    ('EliteSniper', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
    ('HyperSpeed', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
    ('TitanShield', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER');

-- Insert statements for session table
INSERT INTO sessions (game_mode, created_by_user_id, start_time, end_time, session_duration)
VALUES
  (0, 1, '2025-06-07 09:30:00', '2025-06-07 10:30:00', '1 hr 0 mins'),
  (1, 2, '2025-06-07 10:15:00', '2025-06-07 11:00:00', '45 mins'),
  (1, 3, '2025-06-07 11:30:00', NULL, '30 mins'),          -- ongoing session
  (0, 4, '2025-06-07 11:10:00', '2025-06-07 12:00:00', '50 mins'),
  (1, 5, '2025-06-07 11:50:00', '2025-06-07 12:30:00', '40 mins'),
  (0, 6, '2025-06-07 12:05:00', NULL, '55 mins'),          -- ongoing session
  (0, 7, '2025-06-07 12:10:00', '2025-06-07 13:15:00', '1 hr 5 mins'),
  (1, 8, '2025-06-07 12:55:00', NULL, '35 mins'),          -- ongoing session
  (1, 9, '2025-06-07 12:50:00', '2025-06-07 14:00:00', '1 hr 10 mins'),
  (0,10, '2025-06-07 13:35:00', '2025-06-07 14:30:00', '55 mins'),
  (1, 1, '2025-06-07 13:45:00', NULL, '45 mins'),          -- ongoing session
  (0, 2, '2025-06-07 14:00:00', '2025-06-07 15:00:00', '1 hr 0 mins'),
  (0, 3, '2025-06-07 14:40:00', '2025-06-07 15:30:00', '50 mins'),
  (1, 4, '2025-06-07 15:20:00', NULL, '40 mins'),          -- ongoing session
  (0, 5, '2025-06-07 14:45:00', '2025-06-07 16:00:00', '1 hr 15 mins');

-- Insert statements for team table
INSERT INTO team (session_id, team_name)
VALUES
  (1, 'Team Alpha'),
  (2, 'Team Bravo'),
  (3, 'Team Charlie'),
  (4, 'Team Delta'),
  (5, 'Team Echo'),
  (6, 'Team Foxtrot'),
  (7, 'Team Golf'),
  (8, 'Team Hotel'),
  (9, 'Team India'),
  (10, 'Team Juliet'),
  (11, 'Team Kilo'),
  (12, 'Team Lima'),
  (13, 'Team Mike'),
  (14, 'Team November'),
  (15, 'Team Oscar');

-- Insert statements for player table
INSERT INTO player (session_id, team_id, user_id, player_name, health, score)
VALUES
  (1, 1, 1, 'Player1', 100,  0),
  (2, 2, 2, 'Player2',  95,  5),
  (3, 3, 3, 'Player3',  90, 10),
  (4, 4, 4, 'Player4',  85, 15),
  (5, 5, 5, 'Player5',  80, 20),
  (6, 6, 6, 'Player6', 100, 25),
  (7, 7, 7, 'Player7',  95, 30),
  (8, 8, 8, 'Player8',  90, 35),
  (9, 9, 9, 'Player9',  85, 40),
  (10, 10,10,'Player10', 80, 45),
  (11, 11, 1, 'Player11',100, 50),
  (12, 12, 2, 'Player12', 95, 55),
  (13, 13, 3, 'Player13', 90, 60),
  (14, 14, 4, 'Player14', 85, 65),
  (15, 15, 5, 'Player15', 80, 70);

-- Insert statements for player_action table
INSERT INTO player_action (session_id, player_id, action_type, amount)
VALUES
  (1,  1, 'score', 10),
  (2,  2, 'health', -5),
  (3,  3, 'score', 15),
  (4,  4, 'health', -10),
  (5,  5, 'score', 20),
  (6,  6, 'health', -15),
  (7,  7, 'score', 25),
  (8,  8, 'health', -20),
  (9,  9, 'score', 30),
  (10, 10,'health', -25),
  (11, 11,'score', 35),
  (12, 12,'health', -30),
  (13, 13,'score', 40),
  (14, 14,'health', -35),
  (15, 15,'score', 45);


COMMIT TRANSACTION;
