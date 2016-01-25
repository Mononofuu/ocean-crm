ALTER TABLE tag ADD subject_type INTEGER;
UPDATE db_version SET version = 1.08;