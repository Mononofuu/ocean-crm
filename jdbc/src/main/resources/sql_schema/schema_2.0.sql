CREATE  TABLE users(
  id SERIAL PRIMARY KEY,
  name varchar(45) ,
  login varchar(45) ,
  password varchar(45),
  photo bytea,
  email varchar(45),
  phone_mob varchar(45),
  phone_work varchar(45),
  language varchar(45),
  comment varchar(200));

CREATE  TABLE subject (
  id SERIAL PRIMARY KEY,
  content_owner_id integer REFERENCES users(id),
  name varchar(45),
  removed BOOLEAN DEFAULT FALSE);

CREATE  TABLE company (
  id integer REFERENCES subject(id) ON DELETE CASCADE PRIMARY KEY,
  phone_number varchar(45),
  email varchar(45),
  web varchar(45),
  address varchar(100),
  date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_updated TIMESTAMP);

CREATE  TABLE phone_type (
  id SERIAL PRIMARY KEY,
  name varchar(45));

CREATE  TABLE contact (
  id integer REFERENCES subject(id) ON DELETE CASCADE PRIMARY KEY,
  post varchar(45),
  phone_type_id integer REFERENCES phone_type(id) ,
  phone varchar(45),
  email varchar(45),
  skype varchar(45),
  company_id integer REFERENCES company(id),
  date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_updated TIMESTAMP);

CREATE  TABLE status_type (
  id SERIAL PRIMARY KEY,
  name varchar(45),
  color VARCHAR(7) NOT NULL DEFAULT '#E0E0E0',
  systemDefault BOOLEAN DEFAULT FALSE);

CREATE  TABLE currency (
  id SERIAL PRIMARY KEY,
  code varchar(10),
  name varchar(45));

CREATE  TABLE deal (
  id integer REFERENCES subject(id) ON DELETE CASCADE PRIMARY KEY,
  status_id integer REFERENCES status_type(id) ,
  currency_id integer REFERENCES currency(id),
  budget integer ,
  contact_main_id integer REFERENCES contact(id),
  company_id integer REFERENCES company(id),
  data_close timestamp,
  created_date TIMESTAMP WITHOUT TIME ZONE,
  responsible_id integer REFERENCES users(id));


CREATE  TABLE settings (
  id SERIAL PRIMARY KEY,
  setting varchar(30),
  value varchar(500));

CREATE  TABLE task_type (
  id SERIAL PRIMARY KEY,
  name varchar(45),
  object_type varchar(2));


CREATE  TABLE role(
  id SERIAL PRIMARY KEY,
  name varchar(45),
  description varchar(200));


CREATE TABLE event(
  id SERIAL PRIMARY KEY,
  event_date timestamp,
  user_id integer REFERENCES users(id),
  operation_type varchar(45),
  content varchar(200));


CREATE TABLE deal_contact(
  deal_id integer  NOT NULL REFERENCES deal(id),
  contact_id integer NOT NULL REFERENCES contact(id),
  PRIMARY KEY(deal_id, contact_id));


CREATE  TABLE task (
  id SERIAL PRIMARY KEY,
  subject_id integer REFERENCES subject(id) ON DELETE CASCADE,
  created_date timestamp,
  due_date timestamp,
  user_id integer REFERENCES users(id),
  task_type_id integer REFERENCES task_type(id),
  comment varchar(200),
  is_closed smallint,
  is_deleted smallint);


CREATE  TABLE file(
  id SERIAL PRIMARY KEY,
  subject_id integer REFERENCES subject(id) ON DELETE CASCADE,
  name varchar(45),
  link varchar(200),
  content bytea,
  created_date timestamp,
  user_id integer REFERENCES users(id),
  size integer);


CREATE TABLE comment (
  id SERIAL PRIMARY KEY,
  subject_id integer REFERENCES subject(id) ON DELETE CASCADE,
  comment varchar(200),
  created_date timestamp,
  user_id integer REFERENCES users(id));


CREATE  TABLE grants(
  user_id integer NOT NULL REFERENCES users(id),
  role_id integer NOT NULL REFERENCES role(id),
  level integer,
  PRIMARY KEY(user_id, role_id));


CREATE  TABLE tag(
  id SERIAL PRIMARY KEY,
  name varchar(45),
  subject_type INTEGER);


CREATE  TABLE subject_tag(
  subject_id integer REFERENCES subject(id) ON DELETE CASCADE,
  tag_id integer REFERENCES tag(id),
  PRIMARY KEY(subject_id, tag_id));

CREATE TABLE db_version(
  version VARCHAR(45) PRIMARY KEY
);

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

INSERT INTO db_version (version) VALUES ('2.0');

INSERT INTO phone_type (name) VALUES
  ('WORK_PHONE_NUMBER'),
  ('WORK_DIRECT_PHONE_NUMBER'),
  ('MOBILE_PHONE_NUMBER'),
  ('FAX_NUMBER'),
  ('HOME_PHONE_NUMBER'),
  ('OTHER_PHONE_NUMBER');

INSERT INTO task_type (name) VALUES
  ('FOLLOW_UP'),
  ('MEETING'),
  ('OTHER');

INSERT INTO currency (code, name) VALUES ('USD', 'Dollar');

INSERT INTO status_type (name, color, systemDefault) VALUES
  ('PRIMARY CONTACT', '#0040ff', FALSE),
  ('CONVERSATION', '#7f00ff', FALSE),
  ('MAKE THE DECISION', '#ffff00', FALSE),
  ('APPROVAL OF THE CONTRACT', '#80ff00', FALSE),
  ('SUCCESS', '#00ff00', TRUE),
  ('CLOSED AND NOT IMPLEMENTED', '#ff0000', TRUE),
  ('DELETED', '#ff0000', TRUE);

