INSERT INTO community (id, name, create_date, description, user_id) VALUES ('1', 'COMMUNITY-1', TIMESTAMP '2017-04-02 13:30:14', 'COMMUNITY-1-DESC', 'USER-1-ID');

INSERT INTO post (id, text, create_date) VALUES ('1', 'TEST1', TIMESTAMP '2017-04-02 13:30:14');

INSERT INTO post_community_assoc (community_id, post_id) VALUES ('1', '1');
