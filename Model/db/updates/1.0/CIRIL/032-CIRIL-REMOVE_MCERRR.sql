DELETE FROM requirement where request_type_id = (select id from request_type where label = 'Multi Cerfa Electoral Roll Registration');
DELETE FROM forms where request_type_id = (select id from request_type where label = 'Multi Cerfa Electoral Roll Registration');
delete from request_type where label = 'Multi Cerfa Electoral Roll Registration';
