CREATE TABLE filter (
  id         SERIAL PRIMARY KEY,
  name       VARCHAR(45)                         NOT NULL,
  user_id    INTEGER REFERENCES users (id)       NOT NULL,
  type       VARCHAR(45)                         NOT NULL,
  date_from  TIMESTAMP,
  date_to    TIMESTAMP,
  status_id  INTEGER REFERENCES status_type (id) NOT NULL,
  manager_id INTEGER REFERENCES contact (id),
  tasks      VARCHAR(45),
  tags       VARCHAR(45)
);