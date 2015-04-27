alter table parking_permit_temporary_relocation_request
    drop constraint FK826DD839DBF56A6;

alter table parking_permit_temporary_relocation_request_equipment_used 
    drop constraint FK1775820ADCF45D04;

alter table parking_permit_temporary_relocation_request_equipment_used 
    drop constraint FK1775820A801C7F07;

alter table parking_permit_temporary_relocation_request_perform_choice 
    drop constraint FKC2280E7B1CD9AEF3;

alter table parking_permit_temporary_relocation_request_perform_choice 
    drop constraint FKC2280E7B801C7F07;

drop table parking_permit_temporary_relocation_request_equipment_used cascade;
drop table parking_permit_temporary_relocation_request_perform_choice cascade;

alter table parking_permit_temporary_relocation_request 
    add column ape_code varchar(5),
    add column furniture_lifting bool,
    add column is_company bool,
    add column observations varchar(255),
    add column observations_reglement varchar(255),
    add column other varchar(255),
    add column payment_indicative_amount varchar(255),
    add column requester_address varchar(255),
    add column siret_number varchar(14),
    add column vehicle_type varchar(255);

alter table parking_permit_temporary_relocation_request 
    drop column largeur,
    drop column marque,
    drop column tonnage,
    drop column volume,
    drop column requester_address_id;

create table parking_permit_temporary_relocation_request_desired_service (
    parking_permit_temporary_relocation_request_id int8 not null,
    desired_service_id int8 not null,
    desired_service_index int4 not null,
    primary key (parking_permit_temporary_relocation_request_id, desired_service_index)
);

alter table parking_permit_temporary_relocation_request_desired_service 
    add constraint FKBC299F5410385382 
    foreign key (desired_service_id)
    references local_referential_data;

alter table parking_permit_temporary_relocation_request_desired_service 
    add constraint FKBC299F54801C7F07 
    foreign key (parking_permit_temporary_relocation_request_id) 
    references parking_permit_temporary_relocation_request;

alter table request_type add column specific_configuration_data TEXT;
