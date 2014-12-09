DELETE FROM requirement where request_type_id in (select id FROM request_type where label = 'Gru Citizen');
DELETE FROM forms where request_type_id in (select id FROM request_type where label = 'Gru Citizen');
DELETE FROM request_type where label = 'Gru Citizen';

DELETE FROM requirement where request_type_id in (select id FROM request_type where label = 'Home Meal Delivery');
DELETE FROM forms where request_type_id in (select id FROM request_type where label = 'Home Meal Delivery');
DELETE FROM request_type where label = 'Home Meal Delivery';

DELETE FROM requirement where request_type_id in (select id FROM request_type where label = 'School Registration Simplify');
DELETE FROM forms where request_type_id in (select id FROM request_type where label = 'School Registration Simplify');
DELETE FROM request_type where label = 'School Registration Simplify';
