ALTER TABLE adult
  ADD COLUMN validation_code VARCHAR(100) default '';
ALTER TABLE adult
  ADD COLUMN validation_code_expiration timestamp without time zone;
ALTER TABLE global_home_folder_configuration
  ADD COLUMN pending_users_live_duration integer NOT NULL DEFAULT 10,
  ADD COLUMN pending_users_notification_purge integer NOT NULL DEFAULT 5;
