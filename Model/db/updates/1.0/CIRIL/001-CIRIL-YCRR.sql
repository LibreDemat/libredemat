create table youth_center_registration_request (
    id int8 not null,
    child_alone bool,
    first_registration_date timestamp,
    first_registration_numero_adherent varchar(255),
    is_first_registration bool,
    multi_activities bool,
    rules_acceptance bool,
    subject_choice_birth_date timestamp,
    subject_choice_email varchar(255),
    subject_choice_mobile_phone varchar(10),
    primary key (id)
);
