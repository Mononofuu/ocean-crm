ALTER TABLE task ADD is_closed smallint;
ALTER TABLE task ADD is_deleted smallint;
UPDATE db_version SET version = 1.06;
