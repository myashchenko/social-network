CREATE TABLE user_friends (
  user_id VARCHAR (255) NOT NULL,
  friend_id VARCHAR (255) NOT NULL
);

ALTER TABLE user_friends ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_friends ADD FOREIGN KEY (friend_id) REFERENCES users(id);