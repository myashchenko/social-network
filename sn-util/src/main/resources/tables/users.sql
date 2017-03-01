CREATE TABLE users (
  id VARCHAR (255) NOT NULL,
  name VARCHAR (255) NOT NULL,
  create_date TIMESTAMP NOT NULL,
  email VARCHAR (255) NOT NULL,
  password VARCHAR (255) NOT NULL,
  last_visit TIMESTAMP,
  avatar_id VARCHAR (255),
  wall_id VARCHAR (255),
  role VARCHAR (255) NOT NULL
);

ALTER TABLE users ADD PRIMARY KEY (id);