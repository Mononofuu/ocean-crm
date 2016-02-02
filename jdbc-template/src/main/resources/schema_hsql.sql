-- CREATE TABLE currency (
--   id   BIGINT GENERATED BY DEFAULT AS IDENTITY (
--   START WITH 1 ) PRIMARY KEY,
--   code VARCHAR(10),
--   name VARCHAR(45)
-- );

CREATE TABLE users
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  name VARCHAR(45),
  login VARCHAR(45),
  password VARCHAR(45),
  photo BLOB,
  email VARCHAR(45),
  phone_mob VARCHAR(45),
  phone_work VARCHAR(45),
  language VARCHAR(45),
  comment VARCHAR(200)
);

CREATE TABLE subject
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  content_owner_id INTEGER,
  name VARCHAR(45),
  removed BOOLEAN DEFAULT false,
  CONSTRAINT subject_content_owner_id_fkey FOREIGN KEY (content_owner_id) REFERENCES users (id)
);

CREATE TABLE comment (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  subject_id INTEGER,
  comment CHARACTER VARYING(200),
  created_date TIMESTAMP WITHOUT TIME ZONE,
  user_id INTEGER,
  FOREIGN KEY (subject_id) REFERENCES subject (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE company
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  phone_number VARCHAR(45),
  email VARCHAR(45),
  web VARCHAR(45),
  address VARCHAR(100),
  date_created TIMESTAMP DEFAULT now(),
  date_updated TIMESTAMP,
  CONSTRAINT company_id_fkey FOREIGN KEY (id) REFERENCES subject (id)
);

CREATE TABLE phone_type
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  name VARCHAR(45)
);

CREATE TABLE role
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  name VARCHAR(45),
  description VARCHAR(200)
);

CREATE TABLE settings
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  setting VARCHAR(30),
  value VARCHAR(500)
);

CREATE TABLE status_type (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  name CHARACTER VARYING(45),
  color CHARACTER VARYING(7) NOT NULL,
  systemdefault BOOLEAN DEFAULT false
);

CREATE TABLE contact
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  post VARCHAR(45),
  phone_type_id INTEGER,
  phone VARCHAR(45),
  email VARCHAR(45),
  skype VARCHAR(45),
  company_id INTEGER,
  date_created TIMESTAMP DEFAULT now(),
  date_updated TIMESTAMP,
  CONSTRAINT contact_id_fkey FOREIGN KEY (id) REFERENCES subject (id),
  CONSTRAINT contact_phone_type_id_fkey FOREIGN KEY (phone_type_id) REFERENCES phone_type (id),
  CONSTRAINT contact_company_id_fkey FOREIGN KEY (company_id) REFERENCES company (id)
);

CREATE TABLE currency
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  code VARCHAR(10),
  name VARCHAR(45)
);

CREATE TABLE deal (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  status_id INTEGER,
  currency_id INTEGER,
  budget INTEGER,
  contact_main_id INTEGER,
  company_id INTEGER,
  data_close TIMESTAMP WITHOUT TIME ZONE,
  created_date TIMESTAMP WITHOUT TIME ZONE,
  responsible_id INTEGER,
  FOREIGN KEY (company_id) REFERENCES company (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (contact_main_id) REFERENCES contact (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (currency_id) REFERENCES currency (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (id) REFERENCES subject (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (responsible_id) REFERENCES users (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (status_id) REFERENCES status_type (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE deal_contact (
  deal_id INTEGER NOT NULL,
  contact_id INTEGER NOT NULL,
  PRIMARY KEY (deal_id, contact_id),
  FOREIGN KEY (contact_id) REFERENCES contact (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (deal_id) REFERENCES deal (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE event (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  event_date TIMESTAMP WITHOUT TIME ZONE,
  user_id INTEGER,
  operation_type CHARACTER VARYING(45),
  content CHARACTER VARYING(200),
  FOREIGN KEY (user_id) REFERENCES users (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE file (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  subject_id INTEGER,
  name CHARACTER VARYING(45),
  link CHARACTER VARYING(200),
  content BLOB,
  created_date TIMESTAMP WITHOUT TIME ZONE,
  user_id INTEGER,
  size INTEGER,
  FOREIGN KEY (subject_id) REFERENCES subject (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE filter (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  name CHARACTER VARYING(45) NOT NULL,
  user_id INTEGER NOT NULL,
  type CHARACTER VARYING(45) NOT NULL,
  date_from TIMESTAMP WITHOUT TIME ZONE,
  date_to TIMESTAMP WITHOUT TIME ZONE,
  status_id INTEGER NOT NULL,
  manager_id INTEGER,
  tasks CHARACTER VARYING(45),
  tags CHARACTER VARYING(45),
  FOREIGN KEY (manager_id) REFERENCES contact (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (status_id) REFERENCES status_type (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (user_id) REFERENCES users (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE grants (
  user_id INTEGER NOT NULL,
  role_id INTEGER NOT NULL,
  level INTEGER,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (role_id) REFERENCES role (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (user_id) REFERENCES users (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tag
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  name VARCHAR(45),
  subject_type INTEGER
);

CREATE TABLE subject_tag (
  subject_id INTEGER NOT NULL,
  tag_id INTEGER NOT NULL,
  PRIMARY KEY (subject_id, tag_id),
  FOREIGN KEY (subject_id) REFERENCES subject (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tag (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE task_type
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  name VARCHAR(45),
  object_type VARCHAR(2)
);

CREATE TABLE task (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) PRIMARY KEY,
  subject_id INTEGER,
  created_date TIMESTAMP WITHOUT TIME ZONE,
  due_date TIMESTAMP WITHOUT TIME ZONE,
  user_id INTEGER,
  task_type_id INTEGER,
  comment CHARACTER VARYING(200),
  FOREIGN KEY (subject_id) REFERENCES subject (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (task_type_id) REFERENCES task_type (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (user_id) REFERENCES users (id)
    MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
