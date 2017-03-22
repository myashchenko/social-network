CREATE TABLE community (
  id VARCHAR (255) NOT NULL,
  create_date TIMESTAMP NOT NULL,
  name VARCHAR (4000) NOT NULL,
  description VARCHAR (255),
  user_id VARCHAR (255) NOT NULL
);

ALTER TABLE community ADD PRIMARY KEY (id);