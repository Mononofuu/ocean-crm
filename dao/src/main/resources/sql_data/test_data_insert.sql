INSERT INTO subject (id, content_owner_id, name) VALUES
  (1, 2, 'company1'),
  (2, 2, 'company2'),
  (3, 2, 'company3'),
  (4, 2, 'company4'),
  (5, 2, 'contact1'),
  (6, 2, 'contact2'),
  (7, 2, 'contact3'),
  (8, 2, 'contact4'),
  (9, 2, 'contact5'),
  (10, 2, 'contact6'),
  (11, 2, 'deal1'),
  (12, 2, 'deal2'),
  (13, 2, 'deal3'),
  (14, 2, 'deal4');

INSERT INTO company (id, phone_number) VALUES
  (1, 'phone_number1'),
  (2, 'phone_number2'),
  (3, 'phone_number3'),
  (4, 'phone_number4');

INSERT INTO contact (id, post, phone_type_id, phone, email, skype, company_id) VALUES
  (5, 'должность', 1, '000 000 00 00', 'contact1@becomejavasenior.com', 'skype1', 1),
  (6, 'должность', 1, '000 000 00 00', 'contact2@becomejavasenior.com', 'skype2', 2),
  (7, 'должность', 1, '000 000 00 00', 'contact3@becomejavasenior.com', 'skype3', 3),
  (8, 'должность', 1, '000 000 00 00', 'contact4@becomejavasenior.com', 'skype4', 4),
  (9, 'должность', 1, '000 000 00 00', 'contact5@becomejavasenior.com', 'skype5', 1),
  (10, 'должность', 1, '000 000 00 00', 'contact6@becomejavasenior.com', 'skype6', 2);

INSERT INTO deal (id, status_id, currency_id, budget, contact_main_id, company_id, data_close, created_date) VALUES
  (11, 1, 1, 1000, 5, 2, '2016-03-06 22:00:00', NOW()),
  (12, 1, 1, 1000, 5, 2, '2016-03-06 22:00:00', NOW()),
  (13, 1, 1, 1000, 5, 2, '2016-03-06 22:00:00', NOW()),
  (14, 1, 1, 1000, 5, 2, '2016-03-06 22:00:00', NOW());

INSERT INTO task (subject_id, created_date, due_date, user_id, task_type_id, comment) VALUES
  (1, NULL, '2016-01-06 22:00:00',2 , 1, 'coment1'),
  (2, NULL, '2016-01-06 23:59:00',2 , 2, 'coment2'),
  (13, NULL, '2016-01-07 22:00:00',2 , 3, 'coment3'),
  (14, NULL, '2016-01-02 20:00:00',2 , 2, 'coment4');

INSERT INTO tag (name) VALUES
  ('тэг1'),
  ('тэг2'),
  ('тэг3'),
  ('тэг4'),
  ('тэг5'),
  ('тэг6'),
  ('тэг7');

