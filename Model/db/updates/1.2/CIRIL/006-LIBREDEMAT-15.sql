ALTER TABLE request_note DROP CONSTRAINT FK4DABB7A251335760;
ALTER TABLE request_action ADD COLUMN attachment bytea;
ALTER TABLE request_action ADD COLUMN attachment_name varchar(255);
ALTER TABLE request_note ADD COLUMN attachment bytea;
ALTER TABLE request_note ADD COLUMN attachment_name varchar(255);
ALTER TABLE request_note ADD COLUMN parent_id int8;
ALTER TABLE request_action ADD COLUMN reply_parent_id int8;
