DROP table electoral_roll_registration_request;

DELETE from request_action where request_id IN (SELECT id from request where request_type_id=(SELECT id from request_type where label ='Electoral Roll Registration'));
DELETE from request where request_type_id=(SELECT id from request_type where label ='Electoral Roll Registration');

