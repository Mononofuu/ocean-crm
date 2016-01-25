CREATE TABLE IF NOT EXISTS filter (
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

ALTER TABLE status_type
ADD color VARCHAR(7) NOT NULL DEFAULT '#E0E0E0',
ADD systemDefault BOOLEAN DEFAULT FALSE;

ALTER TABLE deal
ADD created_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE deal ADD responsible_id integer REFERENCES users(id);

ALTER TABLE task ADD is_closed smallint, ADD is_deleted smallint;

ALTER TABLE subject ADD removed BOOLEAN DEFAULT FALSE;

ALTER TABLE contact ADD date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD date_updated TIMESTAMP;

ALTER TABLE company ADD date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD date_updated TIMESTAMP;

ALTER TABLE tag ADD subject_type INTEGER;

UPDATE db_version SET version = 2.0;
