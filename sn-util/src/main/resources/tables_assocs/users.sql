ALTER TABLE users ADD FOREIGN KEY (avatar_id) REFERENCES file(id);

ALTER TABLE users ADD FOREIGN KEY (wall_id) REFERENCES user_wall(id);