ALTER TABLE deal
ADD created_date TIMESTAMP WITHOUT TIME ZONE;
UPDATE db_version SET version = 1.03;