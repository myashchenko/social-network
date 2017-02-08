CREATE TABLE post (
  id VARCHAR (255) NOT NULL,
  create_date TIMESTAMP NOT NULL,
  text VARCHAR (4000) NOT NULL,
  community_wall_id VARCHAR (255),
  user_wall_id VARCHAR (255)
);

ALTER TABLE post ADD PRIMARY KEY (id);