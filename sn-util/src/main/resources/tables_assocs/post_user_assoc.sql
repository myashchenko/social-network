ALTER TABLE post_user_assoc ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE post_user_assoc ADD FOREIGN KEY (post_id) REFERENCES post(id);