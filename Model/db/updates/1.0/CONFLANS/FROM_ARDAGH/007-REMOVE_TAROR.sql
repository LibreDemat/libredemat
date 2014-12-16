begin;
-- Delete from request related tables.

delete from request_action ra
    where ra.request_id in
        (select r.id from request r, request_type rt
            where r.request_type_id = rt.id
            and   rt.label = 'Taverny Approval Road Occupancy');

delete from request_document rd using request r, request_type rt
    where rd.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'Taverny Approval Road Occupancy';

delete from request_lock rl using request r, request_type rt
    where rl.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'Taverny Approval Road Occupancy';

delete from request_note rn using request r, request_type rt
    where rn.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'Taverny Approval Road Occupancy';

-- Delete requests.

delete from request r using request_type rt
    where r.request_type_id = rt.id
    and   rt.label = 'Taverny Approval Road Occupancy';

-- Delete request specific data.

drop table taverny_approval_road_occupancy_request cascade;

-- Delete from request_type related tables.

delete from request_form rf using forms f, request_type rt
    where f.request_form_id = rf.id
    and   f.request_type_id = rt.id
    and   rt.label = 'Taverny Approval Road Occupancy';

delete from request_season rs using request_type rt
    where rs.request_type_id = rt.id
    and   rt.label = 'Taverny Approval Road Occupancy';

delete from requirement r using request_type rt
    where r.request_type_id = rt.id
    and   rt.label = 'Taverny Approval Road Occupancy';

-- Delete request type.

delete from request_type
    where label = 'Taverny Approval Road Occupancy';

drop table taverny_approval_road_occupancy_request_choix_objet_demande;

commit;
