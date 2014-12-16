begin;
-- Delete from request related tables.

delete from request_action ra
    where ra.request_id in
        (select r.id from request r, request_type rt
            where r.request_type_id = rt.id
            and   rt.label = 'Pessac Perischool Activity Registration');

delete from request_document rd using request r, request_type rt
    where rd.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'Pessac Perischool Activity Registration';

delete from request_lock rl using request r, request_type rt
    where rl.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'Pessac Perischool Activity Registration';

delete from request_note rn using request r, request_type rt
    where rn.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'Pessac Perischool Activity Registration';

-- Delete requests.

delete from request r using request_type rt
    where r.request_type_id = rt.id
    and   rt.label = 'Pessac Perischool Activity Registration';

-- Delete request specific data.

drop table pessac_perischool_activity_registration_request cascade;

-- Delete from request_type related tables.

delete from request_form rf using forms f, request_type rt
    where f.request_form_id = rf.id
    and   f.request_type_id = rt.id
    and   rt.label = 'Pessac Perischool Activity Registration';

delete from request_season rs using request_type rt
    where rs.request_type_id = rt.id
    and   rt.label = 'Pessac Perischool Activity Registration';

delete from requirement r using request_type rt
    where r.request_type_id = rt.id
    and   rt.label = 'Pessac Perischool Activity Registration';

-- Delete request type.

delete from request_type
    where label = 'Pessac Perischool Activity Registration';

drop table pessac_perischool_activity_registration_request_pparr_freq_peri;
drop table pessac_perischool_activity_registration_request_pparr_freq_resto;
drop table pessac_perischool_activity_registration_request_pparr_freq_trans;
drop table pessac_perischool_activity_registration_request_pparr_freq_ateliers;

commit;
