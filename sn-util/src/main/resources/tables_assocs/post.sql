ALTER TABLE post ADD FOREIGN KEY (community_wall_id) REFERENCES community_wall (id);
ALTER TABLE post ADD FOREIGN KEY (user_wall_id) REFERENCES user_wall (id);