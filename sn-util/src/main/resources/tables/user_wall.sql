CREATE TABLE user_wall (
  id VARCHAR (255) NOT NULL,
  create_date TIMESTAMP NOT NULL,
  user_id VARCHAR (255) NOT NULL
);

ALTER TABLE user_wall ADD PRIMARY KEY (id);