INSERT INTO communities (id, name, created, description, user_id) VALUES ('1', 'COMMUNITY-1', TIMESTAMP '2017-04-02 13:30:14', 'COMMUNITY-1-DESC', 'USER-1-ID');

INSERT INTO posts (id, text, created, user_id, community_id) VALUES ('1', 'TEST1', TIMESTAMP '2017-04-02 13:30:14', 'USER-2-ID', '1');
