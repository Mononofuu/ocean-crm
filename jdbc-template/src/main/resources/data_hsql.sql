INSERT INTO CURRENCY (code, name) VALUES ('USD', 'Dollar');
INSERT INTO CURRENCY (code, name) VALUES ('EUR', 'Euro');
INSERT INTO CURRENCY (code, name) VALUES ('UAH', 'Hryvna');

INSERT INTO phone_type(id, name) VALUES (1, 'FAX_NUMBER');
INSERT INTO phone_type(id, name) VALUES (2, 'HOME_PHONE_NUMBER');
INSERT INTO phone_type(id, name) VALUES (3, 'MOBILE_PHONE_NUMBER');
INSERT INTO phone_type(id, name) VALUES (4, 'OTHER_PHONE_NUMBER');
INSERT INTO phone_type(id, name) VALUES (5, 'WORK_DIRECT_PHONE_NUMBER');
INSERT INTO phone_type(id, name) VALUES (6, 'WORK_PHONE_NUMBER');

INSERT INTO status_type (name, color, systemDefault) VALUES
  ('PRIMARY CONTACT', '#0040ff', FALSE),
  ('CONVERSATION', '#7f00ff', FALSE),
  ('MAKE THE DECISION', '#ffff00', FALSE),
  ('APPROVAL OF THE CONTRACT', '#80ff00', FALSE),
  ('SUCCESS', '#00ff00', TRUE),
  ('CLOSED AND NOT IMPLEMENTED', '#ff0000', TRUE),
  ('DELETED', '#ff0000', TRUE);

INSERT INTO task_type(id, name) VALUES (1, 'FOLLOW_UP');
INSERT INTO task_type(id, name) VALUES (2, 'MEETING');
INSERT INTO task_type(id, name) VALUES (3, 'OTHER');

INSERT INTO users (name, login, password) VALUES
  ('user', 'user', 'user01'),
  ('user2', 'user', 'user');