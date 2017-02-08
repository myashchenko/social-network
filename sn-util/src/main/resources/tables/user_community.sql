CREATE TABLE user_community (
  user_id VARCHAR (255) NOT NULL,
  community_id VARCHAR (255) NOT NULL
);

ALTER TABLE user_community ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE user_community ADD FOREIGN KEY (community_id) REFERENCES community (id);