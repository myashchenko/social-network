ALTER TABLE community ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE community ADD FOREIGN KEY (wall_id) REFERENCES community_wall (id);