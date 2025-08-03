-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users, player, sessions, game_mode, player_action, team, user_session CASCADE;

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

CREATE TABLE game_mode (
    mode_id SERIAL,
    mode_name varchar(75) UNIQUE NOT NULL,
    CONSTRAINT PK_mode PRIMARY KEY (mode_id)
);

--sessions (name is pluralized because 'session' is a SQL keyword)
CREATE TABLE sessions (
    session_id SERIAL,
    mode_id int NOT NULL,
    created_by_user_id int NOT NULL,
    start_time timestamp DEFAULT CURRENT_TIMESTAMP,
    end_time timestamp,
    timer_duration int NOT NULL,
    CONSTRAINT PK_session PRIMARY KEY (session_id),
    CONSTRAINT FK_mode FOREIGN KEY (mode_id) REFERENCES game_mode(mode_id),
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
    team_id int,
    user_id int,
    name varchar(100) NOT NULL,
    health int NOT NULL CHECK (health >= 0),
    score int DEFAULT 0,
    CONSTRAINT PK_player PRIMARY KEY (player_id),
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

CREATE TABLE user_session (
    user_session_id SERIAL,
    user_id int NOT NULL,
    session_id int NOT NULL,
    session_joined timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_user_session PRIMARY KEY (user_session_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT FK_session FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE
);

COMMIT TRANSACTION;
