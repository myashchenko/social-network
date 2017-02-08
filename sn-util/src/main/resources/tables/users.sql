CREATE TABLE users (
  id VARCHAR (255) NOT NULL,
  name VARCHAR (255) NOT NULL,
  create_date TIMESTAMP NOT NULL,
  email VARCHAR (255) NOT NULL,
  password VARCHAR (255) NOT NULL,
  last_visit TIMESTAMP,
  avatar_id VARCHAR (255),
  wall_id VARCHAR (255)
);

ALTER TABLE users ADD PRIMARY KEY (id);

ALTER TABLE users ADD FOREIGN KEY (avatar_id) REFERENCES file(id);

ALTER TABLE user_wall ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE users ADD FOREIGN KEY (wall_id) REFERENCES user_wall(id);
ALTER TABLE file ADD FOREIGN KEY (user_id) REFERENCES users(id);