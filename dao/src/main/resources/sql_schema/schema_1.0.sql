---drop schema public cascade;
---create schema public;

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
  name varchar(45));

CREATE  TABLE company (
  id integer REFERENCES subject(id) ON DELETE CASCADE PRIMARY KEY,
  phone_number varchar(45),
  email varchar(45),
  web varchar(45),
  address varchar(100));

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
  company_id integer REFERENCES company(id));

CREATE  TABLE status_type (
  id SERIAL PRIMARY KEY,
  name varchar(45));

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
  data_close timestamp);


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
  comment varchar(200));


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
  name varchar(45));


CREATE  TABLE subject_tag(
  subject_id integer REFERENCES subject(id) ON DELETE CASCADE,
  tag_id integer REFERENCES tag(id),
  PRIMARY KEY(subject_id, tag_id));

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

CREATE TABLE db_version(
  version VARCHAR(45) PRIMARY KEY
);

INSERT INTO db_version (version) VALUES ('1.0');