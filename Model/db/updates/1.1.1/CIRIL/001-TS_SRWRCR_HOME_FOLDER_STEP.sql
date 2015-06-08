DELETE FROM request_action 
WHERE request_id IN ( 
    select id FROM request 
    WHERE state='DRAFT' 
    AND request_type_id IN ( SELECT id FROM request_type WHERE label='School Registration With Remote Cirilnetenfance' )
);

DELETE FROM request 
WHERE state='DRAFT' 
AND request_type_id IN ( SELECT id FROM request_type WHERE label='School Registration With Remote Cirilnetenfance' );
