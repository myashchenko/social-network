INSERT INTO post (id, text, create_date) VALUES ('1', 'TEST1', CURRENT_TIMESTAMP);
INSERT INTO post (id, text, create_date) VALUES ('2', 'TEST2', CURRENT_TIMESTAMP);
INSERT INTO post (id, text, create_date) VALUES ('3', 'TEST3', CURRENT_TIMESTAMP);

INSERT INTO users (id, name, create_date, email, password, role) VALUES ('post-user-1', 'POST-USER-1', CURRENT_TIMESTAMP, 'POST-USER-1@EMAIL.COM', '11111111', 'USER');
INSERT INTO community (id, name, create_date, user_id) VALUES ('post-community-1', 'com1', CURRENT_TIMESTAMP, 'post-user-1');

INSERT INTO post_user_assoc (user_id, post_id) VALUES ('post-user-1', '2');

INSERT INTO post_community_assoc (community_id, post_id) VALUES ('post-community-1', '3');