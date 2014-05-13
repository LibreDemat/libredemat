ALTER TABLE request_type
  ADD COLUMN is_of_registration_kind bool,
  ADD COLUMN subject_policy varchar(255),
  ADD COLUMN support_multiple bool,
  ADD COLUMN support_unregistered_creation bool,
  ADD COLUMN filing_delay int4;
