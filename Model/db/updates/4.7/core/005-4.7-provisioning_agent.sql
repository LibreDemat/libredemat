ALTER TABLE agent
	ADD COLUMN validation_code varchar(100),
	ADD COLUMN validation_code_expiration timestamp without time zone,
	ADD COLUMN password character varying(255);	