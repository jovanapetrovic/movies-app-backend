-- PROVIDE DATABASE on localhost
CREATE SCHEMA `movies-app`;


-- TO INITIALIZE TABLES run backend with AppRunner


-- CHOOSE DB
USE `movies-app`;


-- ADD MANDATORY ROLES
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');

-- ADD USERS
-- Password is: 123456
INSERT INTO users (id, date_created, date_updated, email, name, password, username) VALUES
    (1, now(), now(), 'jovanap@mailinator.com', 'Jovana Petrovic', '$2a$10$0wZNzdZWQ0Sy.nnCJ3JDHuk23Tb6F2hW/WFNbSWwKAYL6Sp3n9iVW', 'jovana'),
    (2, now(), now(), 'nikolan@mailinator.com', 'Nikola Nikolic', '$2a$10$0wZNzdZWQ0Sy.nnCJ3JDHuk23Tb6F2hW/WFNbSWwKAYL6Sp3n9iVW', 'nikola'),
    (3, now(), now(), 'petarp@mailinator.com', 'Petar Peric', '$2a$10$0wZNzdZWQ0Sy.nnCJ3JDHuk23Tb6F2hW/WFNbSWwKAYL6Sp3n9iVW', 'petar'),
    (4, now(), now(), 'marijam@mailinator.com', 'Marija Maric', '$2a$10$0wZNzdZWQ0Sy.nnCJ3JDHuk23Tb6F2hW/WFNbSWwKAYL6Sp3n9iVW', 'marija'),
    (5, now(), now(), 'markom@mailinator.com', 'Marko Markovic', '$2a$10$0wZNzdZWQ0Sy.nnCJ3JDHuk23Tb6F2hW/WFNbSWwKAYL6Sp3n9iVW', 'marko'),
    (6, now(), now(), 'anaa@mailinator.com', 'Ana Andric', '$2a$10$0wZNzdZWQ0Sy.nnCJ3JDHuk23Tb6F2hW/WFNbSWwKAYL6Sp3n9iVW', 'ana');

-- ADD ROLES TO USERS
INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2);

-- ADD MANDATORY CATEGORIES
INSERT INTO categories (id, name) VALUES
    (1, 'Action'),
    (2, 'Animation'),
    (3, 'Comedy'),
    (4, 'Crime'),
    (5, 'Documentary'),
    (6, 'Drama'),
    (7, 'Film noir'),
    (8, 'Horror'),
    (9, 'Musical'),
    (10, 'Romance'),
    (11, 'Sci-Fi'),
    (12, 'Thriller');

-- ADD POLLS
INSERT INTO polls (id, created_by, date_created, date_updated, updated_by, expiration_date_time, question) VALUES
    (1, 1, now(), now(), 1, now() + INTERVAL 5 DAY, 'Lord Of The Rings or Harry Potter?'),
    (2, 2, now(), now(), 2, now() + INTERVAL 7 DAY, 'Star Wars vs Star Trek?'),
    (3, 3, now(), now(), 3, now() + INTERVAL 3 HOUR, 'Which movie should I watch tonight?'),
    (4, 1, now(), now(), 1, now() + INTERVAL 3 DAY, 'Which part of the trilogy is the best?'),
    (5, 2, now(), now(), 2, now() - INTERVAL 1 DAY, 'Do you prefer original movies or remakes?');

-- ADD POLL OPTIONS
INSERT INTO options (id, text, poll_id) VALUES
    (1, 'Lord Of The Rings', 1),
    (2, 'Harry Potter', 1),
    (3, 'Star Wars', 2),
    (4, 'Star Trek', 2),
    (5, 'Movie One', 3),
    (6, 'Movie Two', 3),
    (7, 'Movie Three', 3),
    (8, 'Movie Four', 3),
    (9, 'Movie Five', 3),
    (10, 'Movie Six', 3),
    (11, 'Before Sunrise', 4),
    (12, 'Before Sunset', 4),
    (13, 'Before Midnight', 4),
    (14, 'Originals', 5),
    (15, 'Remakes', 5),
    (16, 'Hmm...both? It depends.', 5);

-- ADD POLL VOTES
INSERT INTO votes (id, created_by, date_created, date_updated, updated_by, option_id, poll_id, user_id) VALUES
    (1, 1, now(), now(), 1, 1, 1, 1),
    (2, 1, now(), now(), 1, 4, 2, 1),
    (3, 1, now(), now(), 1, 11, 4, 1),
    (4, 2, now(), now(), 2, 1, 1, 2),
    (5, 2, now(), now(), 2, 3, 2, 2),
    (6, 2, now(), now(), 2, 7, 3, 2),
    (7, 2, now(), now(), 2, 16, 5, 2),
    (8, 3, now(), now(), 3, 2, 1, 3),
    (9, 3, now(), now(), 3, 9, 3, 3),
    (10, 4, now(), now(), 4, 16, 5, 4),
    (11, 4, now(), now(), 4, 10, 3, 4),
    (12, 4, now(), now(), 4, 2, 1, 4),
    (13, 5, now(), now(), 5, 1, 1, 5),
    (14, 5, now(), now(), 5, 3, 2, 5),
    (15, 6, now(), now(), 6, 16, 5, 6);

-- ADD MOVIES
INSERT INTO movies (id, created_by, date_created, date_updated, updated_by, comment, name, rating, year, category_id) VALUES
    (1, 1, now(), now(), 1, 'Very artistic movie!', 'Cold war', 8, 2018, 6),
    (2, 2, now(), now(), 2, 'Awesome movie with a great twist!', 'Fight club', 9, 1999, 6),
    (3, 1, now(), now(), 1, 'Great plot & smart dialogs!', 'Dial M for Murder', 8, 1954, 12),
    (4, 1, now(), now(), 1, 'One of my favourite movies!', 'Bram Stokers Dracula', 8, 1992, 8),
    (5, 2, now(), now(), 2, 'Awesome movie!', 'Star Wars', 8, 1978, 11),
    (6, 2, now(), now(), 2, 'Awesome movie!', 'Underground', 9, 1995, 6),
    (7, 1, now(), now(), 1, 'Its alright, but not too good.', 'Action test movie', 6, 1982, 1),
    (8, 1, now(), now(), 1, 'Jeez, it was so boring and animation was so bad...', 'Animation test movie', 5, 2000, 2),
    (9, 1, now(), now(), 1, 'I was laughing out loud for this one!', 'Comedy test movie', 7, 2005, 3),
    (10, 1, now(), now(), 1, 'Loved the storytelling!', 'Crime test movie', 9, 1962, 4),
    (11, 1, now(), now(), 1, 'Brilliant documentary!', 'Documentary test movie', 10, 1993, 5),
    (12, 1, now(), now(), 1, 'Had a nice evening watching this one.', 'Drama test movie', 8, 2017, 6),
    (13, 1, now(), now(), 1, 'Not bad, but cannot be compared to other film noir classics.', 'Film noir test movie', 6, 2004, 7),
    (14, 1, now(), now(), 1, 'Gave it a chance, but it was awful.', 'Horror test movie', 5, 1981, 8),
    (15, 1, now(), now(), 1, 'Such a fun musical!', 'Musical test movie', 7, 2011, 9),
    (16, 1, now(), now(), 1, 'Who knew a romance movie can be this good?', 'Romance test movie', 10, 2016, 10);









