ALTER TABLE status_type
ADD color VARCHAR(7) NOT NULL DEFAULT '#E0E0E0',
ADD systemDefault BOOLEAN DEFAULT FALSE;

INSERT INTO status_type (name, color, systemDefault) VALUES
  ('PRIMARY CONTACT', '#0040ff', FALSE),
  ('CONVERSATION', '#7f00ff', FALSE),
  ('MAKE THE DECISION', '#ffff00', FALSE),
  ('APPROVAL OF THE CONTRACT', '#80ff00', FALSE),
  ('SUCCESS', '#00ff00', TRUE),
  ('CLOSED AND NOT IMPLEMENTED', '#ff0000', TRUE);