CREATE TABLE IF NOT EXISTS users(
  id_user BIGSERIAL,
  user_name VARCHAR(30),
  pwd VARCHAR(20),
  first_name VARCHAR(20),
  last_name VARCHAR(20),
  role VARCHAR(15),
  PRIMARY KEY (id_user)
);