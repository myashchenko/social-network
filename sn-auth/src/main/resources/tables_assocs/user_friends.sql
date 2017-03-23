ALTER TABLE user_friends ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_friends ADD FOREIGN KEY (friend_id) REFERENCES users(id);