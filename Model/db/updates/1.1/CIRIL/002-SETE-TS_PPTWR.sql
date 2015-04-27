drop table if exists parking_permit_temporary_work_request cascade;

create table parking_permit_temporary_work_request (
        id int8 not null,
        acceptation_reglement_interieur bool,
        ape_code varchar(5),
        construct_license_number varchar(255),
        is_company bool,
        observations varchar(255),
        observations_reglement varchar(255),
        occupation float8,
        occupation_end_date timestamp,
        occupation_other_address varchar(255),
        occupation_start_date timestamp,
        payment_indicative_amount varchar(255),
        reference_number varchar(255),
        scaffolding bool,
        scaffolding_end_date timestamp,
        scaffolding_length float8,
        scaffolding_start_date timestamp,
        siret_number varchar(14),
        site_address varchar(255),
        used_vehicles varchar(255),
        vehicle_parking_or_floor_occupation bool,
        work_nature varchar(255),
        work_on_building bool,
        payment_id int8,
        primary key (id)
    );

    create table parking_permit_temporary_work_request_desired_service (
        parking_permit_temporary_work_request_id int8 not null,
        desired_service_id int8 not null,
        desired_service_index int4 not null,
        primary key (parking_permit_temporary_work_request_id, desired_service_index)
    );

    alter table parking_permit_temporary_work_request 
        add constraint FK40BE34ECFC859B01 
        foreign key (payment_id) 
        references payment;

    alter table parking_permit_temporary_work_request_desired_service 
        add constraint FKB6DF04BD10385382 
        foreign key (desired_service_id) 
        references local_referential_data;

    alter table parking_permit_temporary_work_request_desired_service 
        add constraint FKB6DF04BD3188D675 
        foreign key (parking_permit_temporary_work_request_id) 
        references parking_permit_temporary_work_request;
