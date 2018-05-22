INSERT INTO users (id, name, created, email, password, role) VALUES ('1', 'USER-1', TIMESTAMP '2017-04-02 13:30:14', 'USER-1@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, created, email, password, role) VALUES ('2', 'USER-2', TIMESTAMP '2017-04-02 13:30:14', 'USER-2@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, created, email, password, role) VALUES ('3', 'USER-3', TIMESTAMP '2017-04-02 13:30:14', 'USER-3@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, created, email, password, role) VALUES ('4', 'USER-4', TIMESTAMP '2017-04-02 13:30:14', 'USER-4@EMAIL.COM', '11111111', 'USER');

INSERT INTO friend_requests (id, from_id, to_id, created) VALUES ('1', '3', '4', TIMESTAMP '2017-04-02 13:30:14');
INSERT INTO friend_requests (id, from_id, to_id, created) VALUES ('2', '1', '4', TIMESTAMP '2017-04-02 13:30:14');

INSERT INTO user_friends (user_id, friend_id) VALUES ('2', '4');
INSERT INTO user_friends (user_id, friend_id) VALUES ('4', '2');

INSERT INTO posts (id, created, text, from_id, to_id) VALUES ('1', TIMESTAMP '2017-04-02 13:30:14', '123', '1', '2')