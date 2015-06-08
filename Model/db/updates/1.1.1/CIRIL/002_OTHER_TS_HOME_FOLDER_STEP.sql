DELETE FROM request_action 
WHERE request_id IN ( 
    select id FROM request 
    WHERE state='DRAFT' 
    AND request_type_id IN ( SELECT id FROM request_type WHERE label='Child Care Center Registration' )
);

DELETE FROM request 
WHERE state='DRAFT' 
AND request_type_id IN ( SELECT id FROM request_type WHERE label='Child Care Center Registration' );

DELETE FROM request_action 
WHERE request_id IN ( 
    select id FROM request 
    WHERE state='DRAFT' 
    AND request_type_id IN ( SELECT id FROM request_type WHERE label='Recreation Activity Poly Registration' )
);

DELETE FROM request 
WHERE state='DRAFT' 
AND request_type_id IN ( SELECT id FROM request_type WHERE label='Recreation Activity Poly Registration' );

DELETE FROM request_action 
WHERE request_id IN ( 
    select id FROM request 
    WHERE state='DRAFT' 
    AND request_type_id IN ( SELECT id FROM request_type WHERE label='Youth Center Registration' )
);

DELETE FROM request 
WHERE state='DRAFT' 
AND request_type_id IN ( SELECT id FROM request_type WHERE label='Youth Center Registration' );
