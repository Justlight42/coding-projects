-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS exercise, review, users, user_exercise, user_exercise_list, exercise_list CASCADE;

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
('Strength Training', 'Bench Press', 'Lie flat on a bench with feet planted firmly on the ground. Grip the barbell slightly wider than shoulder-width. Lower the bar slowly to your chest, keeping elbows at a 45-degree angle. Press the bar upward until arms are fully extended. Maintain control throughout the movement and avoid bouncing the bar off your chest.'),
('Cardio', 'Treadmill', 'Walk briskly at 3.0–3.5 mph with a 1–2% incline for 20 minutes to improve endurance and cardiovascular health. For added intensity, increase incline to 4–6% for 5 minutes. Maintain upright posture, swing arms naturally, and avoid leaning on the handrails. Focus on steady breathing and consistent pace throughout.'),
('Core Training', 'Sit-up', 'Lie on your back with knees bent and feet flat on the floor. Cross your arms over your chest or place hands behind your head without pulling. Engage your core and lift your upper body toward your knees. Lower back down with control and repeat. Keep your feet grounded and avoid using momentum.'),
('Strength Training', 'Deadlift', 'Stand with feet hip-width apart and barbell over midfoot. Grip the bar just outside your knees with a flat back and chest up. Engage your core and drive through your heels to lift the bar, extending hips and knees simultaneously. Stand tall at the top, then lower the bar with control by hinging at the hips and bending the knees. Keep the bar close to your body throughout the movement.'),
('Strength Training', 'Pull-up', 'Grip a pull-up bar with palms facing away, hands slightly wider than shoulder-width. Hang with arms fully extended and engage your back and core. Pull your body upward until your chin passes the bar. Lower yourself slowly back to the starting position. Avoid swinging or using momentum to complete the movement.'),
('Strength Training', 'Dips', 'Grip parallel bars and lift your body with arms fully extended. Lower yourself by bending your elbows until your upper arms are about parallel to the ground. Push back up to the starting position by straightening your arms. Keep your torso upright for triceps focus, or lean slightly forward to target the chest.'),
('Strength Training', 'Squats', 'Stand with feet shoulder-width apart and toes slightly turned out. Keep your chest up and back straight. Bend your knees and hips to lower your body until your thighs are parallel to the ground. Push through your heels to return to standing. Maintain a neutral spine and avoid letting your knees cave inward.'),
('Strength Training', 'Leg Curl', 'Lie face down on a leg curl machine with ankles positioned under the padded lever. Grip the handles and keep hips pressed into the bench. Flex your knees to pull the pad toward your glutes. Pause briefly at the top, then lower the weight back down with control. Avoid lifting your hips or using momentum.'),
('Strength Training', 'Calf Raise', 'Stand with feet hip-width apart and toes pointing forward. Push through the balls of your feet to lift your heels as high as possible. Pause briefly at the top, then lower your heels back down with control. Keep your core engaged and avoid rocking your body. Perform on flat ground or a raised surface for greater range of motion.'),
('Strength Training', 'Shoulder Press', 'Hold a barbell or dumbbells at shoulder height with palms facing forward. Stand or sit with a straight back and engaged core. Press the weight overhead until arms are fully extended without locking the elbows. Lower the weight back to shoulder level with control. Keep your wrists aligned and avoid arching your lower back.'),
('Core Training', 'Plank', 'Hold a straight body position with forearms and toes on the ground. Keep your core tight and hips level. Avoid arching your back or letting your hips sag.'),
('Cardio', 'Burpee', 'Squat down, jump into a plank, perform a push-up, jump back in, and leap upward. Repeat continuously for full-body cardio.'),
('Cardio', 'Jump Rope', 'Swing the rope overhead and jump with both feet. Maintain a steady rhythm and light landings.');

-- Insert statements for review table
INSERT INTO review (exercise_id, user_id, rating, comment)
VALUES
(12, 6, 4, 'I hate them, but they work.'),
(6, 10, 3, 'Hurts my shoulders a bit.'),
(7, 1, 3, 'Tough on my knees, but effective.'),
(4, 3, 4, 'Love it, but form is tricky at first.'),
(13, 9, 5, 'I use it for warm-ups and conditioning.'),
(5, 6, 4, 'I use resistance bands to assist. Still a challenge.'),
(13, 3, 5, 'Fun and effective cardio.'),
(3, 6, 2, 'Not my favorite—prefer planks for core stability.'),
(8, 2, 4, 'Good isolation for hamstrings.'),
(1, 1, 5, 'Classic lift. Nothing beats the bench for upper body gains.'),
(9, 4, 4, 'I do these on a step for better range.'),
(2, 6, 4, 'Great cardio, but gets boring without music.'),
(9, 1, 5, 'Simple and effective for calf growth.'),
(10, 5, 5, 'One of my favorite pressing exercises.'),
(4, 10, 4, 'Heavy and intense—great for serious training.'),
(5, 1, 3, 'Hard to do more than a few reps.'),
(7, 6, 4, 'I use a belt for heavy sets. Great compound lift.'),
(11, 9, 5, 'Simple but brutal.'),
(2, 3, 5, 'Perfect for rainy days. Easy to adjust speed and incline.'),
(8, 5, 3, 'Works well, but I prefer Romanian deadlifts.'),
(13, 4, 3, 'Hard on my knees after a while.'),
(3, 7, 5, 'Great addition to my morning routine.'),
(6, 3, 5, 'Excellent for triceps and chest.'),
(1, 7, 4, 'Good results, but I plateaued after a few months.'),
(4, 5, 5, 'Deadlifts changed my physique. Highly recommend.'),
(11, 4, 3, 'Gets boring fast, but it works.'),
(2, 1, 3, 'Works well, but I prefer running outdoors.'),
(10, 2, 5, 'Great for building shoulder strength.'),
(3, 2, 3, 'Works the abs, but strains my neck sometimes.'),
(12, 10, 5, 'Best fat-burning move I know.'),
(5, 7, 4, 'Really targets the lats and arms.'),
(13, 7, 4, 'Good coordination builder.'),
(1, 5, 4, 'Solid movement, but I prefer dumbbells for shoulder comfort.'),
(6, 5, 4, 'I feel strong doing these. Great pump.'),
(3, 4, 4, 'Simple and effective core exercise.'),
(11, 3, 5, 'Amazing for core stability.'),
(7, 4, 5, 'The king of leg exercises. Builds strength fast.'),
(8, 6, 4, 'Nice complement to squats.'),
(12, 2, 5, 'Intense full-body cardio.'),
(10, 6, 4, 'I alternate between dumbbells and barbell.'),
(8, 3, 4, 'Easy to use and targets the right muscles.'),
(4, 8, 3, 'Good lift, but I need lower back support.'),
(9, 8, 3, 'Good burn, but easy to skip.'),
(1, 2, 5, 'Great for building chest strength. I use it every week.'),
(9, 9, 4, 'Quick and easy to add to any workout.'),
(2, 8, 4, 'Good for steady-state cardio and warm-ups.'),
(7, 7, 4, 'I do squats twice a week. Solid gains.'),
(12, 5, 4, 'Great for HIIT workouts.'),
(5, 2, 5, 'Tough but rewarding. Great for upper body strength.'),
(10, 10, 3, 'Hard to keep good form when heavy.'),
(11, 7, 4, 'I hold for 60 seconds daily.'),
(8, 10, 5, 'Great machine for leg day.'),
(3, 9, 4, 'I feel the burn after 3 sets. Solid core move.'),
(7, 9, 5, 'Squats transformed my lower body.'),
(6, 8, 4, 'Good bodyweight move. I do them after bench press.'),
(10, 1, 4, 'Solid upper body move.'),
(4, 1, 5, 'Best full-body strength exercise. Builds power fast.'),
(13, 8, 4, 'Portable and great for travel workouts.'),
(1, 9, 3, 'Effective, but I need a spotter to feel safe.'),
(11, 8, 4, 'Great for posture and abs.'),
(2, 10, 5, 'Helped me train for my first 5K. Very reliable.'),
(7, 1, 3, 'Tough on my knees, but effective.'),
(5, 9, 5, 'One of my favorite bodyweight exercises.'),
(6, 2, 5, 'Simple setup, big results.'),
(12, 1, 3, 'Too hard for beginners.');

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


-- Insert statements for user_exercise_list table
INSERT INTO user_exercise_list (user_id, list_name)
VALUES
(1, 'Chest & Cardio Blast'),
(1, 'Flexibility Routine'),
(1, 'Core Burnout'),
(2, 'Morning Warmup'),
(2, 'Leg Day Express'),
(3, 'Heavy Strength'),
(3, 'Core & Conditioning'),
(4, 'Recovery Moves'),
(4, 'Upper Body Builder'),
(5, 'All-Time Favorites'),
(5, 'Hamstring Focus'),
(5, 'Push Day'),
(6, 'Leg Power Circuit'),
(7, 'Back & Biceps'),
(7, 'Lower Body Blast'),
(8, 'Cardio & Core'),
(8, 'Pull-Up Progression'),
(9, 'Shoulder Shred'),
(9, 'Deadlift Domination'),
(10, 'Full Body Starter'),
(10, 'Bench & Burn'),
(10, 'Quick Core Routine');


-- Insert statements for exercise_list table
INSERT INTO exercise_list (list_id, exercise_id)
VALUES
(1, 1),  -- Bench Press
(1, 2),  -- Treadmill
(2, 3),  -- Sit-up
(2, 9),  -- Calf Raise
(3, 3),  -- Sit-up
(3, 11), -- Plank
(3, 12), -- Burpee
(4, 2),  -- Treadmill
(4, 11), -- Plank
(4, 13), -- Jump Rope
(5, 7),  -- Squats
(5, 8),  -- Leg Curl
(5, 9),  -- Calf Raise
(6, 4),  -- Deadlift
(6, 7),  -- Squats
(6, 10), -- Shoulder Press
(7, 3),  -- Sit-up
(7, 11), -- Plank
(7, 12), -- Burpee
(8, 6),  -- Dips
(8, 9),  -- Calf Raise
(8, 11), -- Plank
(9, 1),  -- Bench Press
(9, 5),  -- Pull-up
(9, 10), -- Shoulder Press
(10, 1), -- Bench Press
(10, 3), -- Sit-up
(10, 7), -- Squats
(10, 12),-- Burpee
(11, 4), -- Deadlift
(11, 8), -- Leg Curl
(12, 1), -- Bench Press
(12, 6), -- Dips
(12, 10),-- Shoulder Press
(13, 7), -- Squats
(13, 8), -- Leg Curl
(13, 9), -- Calf Raise
(14, 4), -- Deadlift
(14, 5), -- Pull-up
(15, 7), -- Squats
(15, 8), -- Leg Curl
(15, 9), -- Calf Raise
(16, 2),  -- Treadmill
(16, 3),  -- Sit-up
(16, 13), -- Jump Rope
(17, 5),  -- Pull-up
(17, 11), -- Plank
(18, 10), -- Shoulder Press
(18, 12), -- Burpee
(19, 4),  -- Deadlift
(19, 11), -- Plank
(20, 2),  -- Treadmill
(20, 3),  -- Sit-up
(20, 7),  -- Squats
(21, 1),  -- Bench Press
(21, 12), -- Burpee
(22, 3),  -- Sit-up
(22, 11); -- Plank

COMMIT TRANSACTION;
