CREATE TABLE IF NOT EXISTS users(
  id_user BIGSERIAL,
  username VARCHAR(30),
  pwd VARCHAR(20),
  name VARCHAR(20),
  last_name VARCHAR(20),
  role VARCHAR(15),
  PRIMARY KEY (id_user)
);