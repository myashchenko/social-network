INSERT INTO users (id, name, create_date, email, password, role) VALUES ('1', 'USER-1', TIMESTAMP '2017-04-02 13:30:14', 'USER-1@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, create_date, email, password, role) VALUES ('2', 'USER-2', TIMESTAMP '2017-04-02 13:30:14', 'USER-2@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, create_date, email, password, role) VALUES ('3', 'USER-3', TIMESTAMP '2017-04-02 13:30:14', 'USER-3@EMAIL.COM', '11111111', 'USER');
INSERT INTO users (id, name, create_date, email, password, role) VALUES ('4', 'USER-4', TIMESTAMP '2017-04-02 13:30:14', 'USER-4@EMAIL.COM', '11111111', 'USER');

INSERT INTO friend_request (id, from_id, to_id, create_date) VALUES ('1', '1', '4', TIMESTAMP '2017-04-02 13:30:14');
INSERT INTO friend_request (id, from_id, to_id, create_date) VALUES ('2', '2', '4', TIMESTAMP '2017-04-02 13:30:14');
INSERT INTO friend_request (id, from_id, to_id, create_date) VALUES ('3', '3', '4', TIMESTAMP '2017-04-02 13:30:14');