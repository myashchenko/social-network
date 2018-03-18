INSERT INTO communities (id, name, created, user_id) VALUES ('post-community-1', 'com1', CURRENT_TIMESTAMP, 'post-user-1');

INSERT INTO posts (id, text, created, community_id, user_id) VALUES ('1', 'TEST1', CURRENT_TIMESTAMP, 'post-community-1', 'post-user-1');
INSERT INTO posts (id, text, created, community_id, user_id) VALUES ('2', 'TEST2', CURRENT_TIMESTAMP, 'post-community-1', 'post-user-1');
INSERT INTO posts (id, text, created, community_id, user_id) VALUES ('3', 'TEST3', CURRENT_TIMESTAMP, 'post-community-1', 'post-user-1');