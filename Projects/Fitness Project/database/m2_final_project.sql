-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS exercise, review, users, user_exercise CASCADE;

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
    rating int NOT NULL DEFAULT 5 CHECK (rating >= 0 AND rating <= 5),
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

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO
    users (username, password_hash, role)
VALUES
    ('johndoe', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('janedoe', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('alexsmith', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('emilyjones', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('michaelbrown', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('sarahwhite', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('davidjohnson', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('lauragarcia', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('chrismartin', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('meganwright', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN');

-- Insert statements for exercise table
INSERT INTO exercise (category, workout_name, description)
VALUES
('Strength Training', 'Bench Press', 'A workout targeting chest muscles.'),
('Cardio', 'Treadmill', 'Running or walking on a treadmill.'),
('Flexibility', 'Yoga', 'Various poses to improve flexibility.'),
('Strength Training', 'Deadlift', 'A compound movement that targets multiple muscle groups.'),
('Cardio', 'Cycling', 'Riding a stationary bike.'),
('Flexibility', 'Stretching', 'Basic stretching exercises.'),
('Strength Training', 'Squats', 'A lower-body workout.'),
('Cardio', 'Rowing', 'Simulated rowing workout.'),
('Flexibility', 'Pilates', 'Core-strengthening exercises.'),
('Strength Training', 'Shoulder Press', 'Targets shoulder muscles.');

-- Insert statements for review table
INSERT INTO review (exercise_id, user_id, rating, comment)
VALUES
(1, 1, 5, 'Great chest workout!'),
(2, 1, 4, 'Keeps me in shape.'),
(3, 2, 5, 'Yoga has been life-changing.'),
(4, 3, 3, 'Deadlifts are tough but effective.'),
(5, 4, 5, 'Cycling is fun and challenging.'),
(6, 5, 2, 'Stretching is boring, but helpful.'),
(7, 6, 4, 'Squats work wonders for the legs.'),
(8, 7, 5, 'Rowing feels like a full-body workout.'),
(9, 8, 5, 'Pilates are great for core strength.'),
(10, 9, 3, 'Shoulder press needs more form.');

-- Insert statements for user_exercise table
INSERT INTO user_exercise (user_id, exercise_id)
VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10),
(10, 1),
(10, 2),
(10, 3),
(9, 4),
(8, 5),
(7, 6),
(5, 8),
(4, 9),
(3, 10);

COMMIT TRANSACTION;
