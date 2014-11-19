create table reservation (
    id int8 not null,
    activity_code varchar(255),
    activity_service_code varchar(255),
    ativity_service_label varchar(255),
    child_id int8,
    color varchar(255),
    date timestamp,
    day_type varchar(255),
    home_folder_id int8,
    session varchar(255),
    primary key (id)
);
