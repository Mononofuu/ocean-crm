ALTER TABLE deal DROP CONSTRAINT deal_contact_main_id_fkey;
ALTER TABLE deal
ADD CONSTRAINT deal_contact_main_id_fkey
FOREIGN KEY (contact_main_id) REFERENCES contact (id);
ALTER TABLE deal ADD responsible_id integer REFERENCES users(id);
UPDATE db_version SET version = 1.05;
