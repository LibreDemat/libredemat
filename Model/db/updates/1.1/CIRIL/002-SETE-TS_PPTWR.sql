create table parking_permit_temporary_work_request (
    id int8 not null,
    acceptation_reglement_interieur bool,
    ape_code varchar(5),
    construct_license_number varchar(255),
    desired_service varchar(255),
    is_company bool,
    observations varchar(255),
    observations_reglement varchar(255),
    occupation float8,
    occupation_end_date timestamp,
    occupation_start_date timestamp,
    payment_indicative_amount varchar(255),
    reference_number varchar(255),
    scaffolding bool,
    scaffolding_end_date timestamp,
    scaffolding_length bytea,
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

alter table parking_permit_temporary_work_request 
    add constraint FK40BE34ECFC859B01 
    foreign key (payment_id) 
    references payment;
