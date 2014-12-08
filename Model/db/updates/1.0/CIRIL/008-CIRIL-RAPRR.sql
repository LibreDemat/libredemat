create table recreation_activity_poly_registration_request (
    id int8 not null,
    child_photo_exploitation_poly_permission bool,
    class_trip_poly_permission bool,
    hospitalization_poly_permission bool,
    rules_and_regulations_poly_acceptance bool,
    urgency_poly_phone varchar(10),
    recreation_poly_center_id int8,
    primary key (id)
    );

create table recreation_activity_poly_registration_request_recreation_poly_activity (
    recreation_activity_poly_registration_request_id int8 not null,
    recreation_poly_activity_id int8 not null,
    recreation_poly_activity_index int4 not null,
    primary key (recreation_activity_poly_registration_request_id, recreation_poly_activity_index)
    );

create table recreation_authorized_poly_individual (
    id int8 not null,
    first_name varchar(38),
    home_phone varchar(10),
    last_name varchar(38),
    office_phone varchar(10),
    address_id int8,
    recreation_activity_poly_registration_request_id int8,
    authorized_poly_individuals_index int4,
    primary key (id)
    );

create table recreation_contact_poly_individual (
    id int8 not null,
    first_name varchar(38),
    home_phone varchar(10),
    last_name varchar(38),
    office_phone varchar(10),
    address_id int8,
    recreation_activity_poly_registration_request_id int8,
    contact_poly_individuals_index int4,
    primary key (id)
    );

alter table recreation_activity_poly_registration_request 
  add constraint FK376F9FB9426DFC62 
  foreign key (recreation_poly_center_id) 
  references recreation_center;

alter table recreation_activity_poly_registration_request_recreation_poly_activity 
  add constraint FK7F62DC7B2A37B0BD 
  foreign key (recreation_poly_activity_id) 
  references local_referential_data;

alter table recreation_activity_poly_registration_request_recreation_poly_activity 
  add constraint FK7F62DC7B87E8988A 
  foreign key (recreation_activity_poly_registration_request_id) 
  references recreation_activity_poly_registration_request;

alter table recreation_authorized_poly_individual 
  add constraint FK306260953525DE03 
  foreign key (address_id) 
  references address;

alter table recreation_authorized_poly_individual 
  add constraint FK3062609587E8988A 
  foreign key (recreation_activity_poly_registration_request_id) 
  references recreation_activity_poly_registration_request;

alter table recreation_contact_poly_individual 
  add constraint FKDB500BA03525DE03 
  foreign key (address_id) 
  references address;

alter table recreation_contact_poly_individual 
  add constraint FKDB500BA087E8988A 
  foreign key (recreation_activity_poly_registration_request_id) 
  references recreation_activity_poly_registration_request;

