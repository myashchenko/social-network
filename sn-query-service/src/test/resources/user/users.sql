INSERT INTO users (id, name, create_date, email, password, role) VALUES ('1', 'USER-1', CURRENT_TIMESTAMP, 'USER-1@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, create_date, email, password, role) VALUES ('2', 'USER-2', CURRENT_TIMESTAMP, 'USER-2@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, create_date, email, password, role) VALUES ('3', 'USER-3', CURRENT_TIMESTAMP, 'USER-3@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, create_date, email, password, role) VALUES ('4', 'USER-4', CURRENT_TIMESTAMP, 'USER-4@EMAIL.COM', '11111111', 'USER');

INSERT INTO user_friends (user_id, friend_id) VALUES ('2', '3');
INSERT INTO user_friends (user_id, friend_id) VALUES ('2', '4');
INSERT INTO user_friends (user_id, friend_id) VALUES ('3', '2');
INSERT INTO user_friends (user_id, friend_id) VALUES ('4', '2');