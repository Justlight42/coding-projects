-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users;

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

CREATE TABLE exercise (
    exercise_id serial,
    category varchar(255) NOT NULL,
    workout_name varchar(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    CONSTRAINT PK_exercise PRIMARY KEY (exercise_id)
);

CREATE TABLE review (
    review_id serial,
    exercise_id int NOT NULL,
    user_id int NOT NULL,
    rating int NOT NULL DEFAULT 5,
    comment TEXT NOT NULL,
    CONSTRAINT PK_review PRIMARY KEY (review_id),
    CONSTRAINT FK_review_exercise FOREIGN KEY (exercise_id) REFERENCES exercise(exercise_id),
    CONSTRAINT FK_review_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE user_exercise (
    user_id int NOT NULL,
    exercise_id int NOT NULL,
    CONSTRAINT PK_user_exercise PRIMARY KEY (user_id, exercise_id),
    CONSTRAINT FK_user_exercise_exercise FOREIGN KEY (exercise_id) REFERENCES exercise(exercise_id),
    CONSTRAINT FK_user_exercise_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE user_exercise_list (
    list_id SERIAL,
    user_id int NOT NULL,
    list_name VARCHAR(255) NOT NULL,
    CONSTRAINT PK_user_exercise_list PRIMARY KEY (list_id),
    CONSTRAINT FK_user_exercise FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT unique_user_listname UNIQUE (user_id, list_name)
);

CREATE TABLE exercise_list (
    list_id int NOT NULL,
    exercise_id int NOT NULL,
    CONSTRAINT PK_exercise_list PRIMARY KEY (list_id, exercise_id),
    CONSTRAINT FK_exercise_list_user FOREIGN KEY (list_id) REFERENCES user_exercise_list(list_id),
    CONSTRAINT FK_exercise_list_exercise FOREIGN KEY (exercise_id) REFERENCES exercise(exercise_id)
);

COMMIT TRANSACTION;
