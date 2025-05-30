BEGIN TRANSACTION;

-- Users
INSERT INTO users (username, password_hash, role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user3','user3','ROLE_USER');

INSERT INTO exercise (category, workout_name, description) VALUES
('Cardio', 'Morning Run', 'A 5km run to start your day.'),
('Strength', 'Push-Up Challenge', 'A set of increasing difficulty push-ups.'),
('Flexibility', 'Yoga Session', 'A 30-minute yoga session for relaxation.');

INSERT INTO review (exercise_id, user_id, rating, comment) VALUES
(1, 1, 5, 'Great way to start the day!'),
(2, 2, 4, 'Challenging but rewarding workout.'),
(3, 3, 5, 'Felt very relaxed after this session.');

INSERT INTO user_exercise (user_id, exercise_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 3),
(3, 1);

COMMIT TRANSACTION;
