begin;
-- Delete from request related tables.

delete from request_action ra
    where ra.request_id in
        (select r.id from request r, request_type rt
            where r.request_type_id = rt.id
            and   rt.label = 'School Registration Simplify');

delete from request_document rd using request r, request_type rt
    where rd.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'School Registration Simplify';

delete from request_lock rl using request r, request_type rt
    where rl.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'School Registration Simplify';

delete from request_note rn using request r, request_type rt
    where rn.request_id = r.id
    and   r.request_type_id = rt.id
    and   rt.label = 'School Registration Simplify';

-- Delete requests.

delete from request r using request_type rt
    where r.request_type_id = rt.id
    and   rt.label = 'School Registration Simplify';

-- Delete request specific data.

drop table school_registration_simplify_request cascade;
drop table school_registration_simplify_request_section cascade;

-- Delete from request_type related tables.

alter table forms
    drop constraint if exists fk5d18c2f38173b49,
    drop constraint if exists fk5d18c2fc5fd0068,
    drop constraint if exists fk5d18c2fd06e9c28,
    add constraint fk5d18c2f38173b49
        foreign key (request_form_id)
        references request_form
        on delete cascade;

delete from request_form rf using forms f, request_type rt
    where f.request_form_id = rf.id
    and   f.request_type_id = rt.id
    and   rt.label = 'School Registration Simplify';

delete from request_season rs using request_type rt
    where rs.request_type_id = rt.id
    and   rt.label = 'School Registration Simplify';

delete from requirement r using request_type rt
    where r.request_type_id = rt.id
    and   rt.label = 'School Registration Simplify';

-- Delete request type.

delete from request_type
    where label = 'School Registration Simplify';

delete from request where specific_data_class = 'org.libredemat.business.request.school.SchoolRegistrationSimplifyRequestData';

commit;
