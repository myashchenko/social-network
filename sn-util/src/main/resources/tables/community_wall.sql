CREATE TABLE community_wall (
  id VARCHAR (255) NOT NULL,
  create_date TIMESTAMP NOT NULL,
  community_id VARCHAR (255) NOT NULL
);

ALTER TABLE community_wall ADD PRIMARY KEY (id);