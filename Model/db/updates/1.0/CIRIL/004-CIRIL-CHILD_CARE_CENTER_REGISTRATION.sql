create table child_care_center_registration_request (
    id int8 not null,
    friday_first_period_begining varchar(255),
    friday_first_period_ending varchar(255),
    friday_period varchar(255),
    friday_second_period_begining varchar(255),
    friday_second_period_ending varchar(255),
    monday_first_period_begining varchar(255),
    monday_first_period_ending varchar(255),
    monday_period varchar(255),
    monday_second_period_begining varchar(255),
    monday_second_period_ending varchar(255),
    registration_date timestamp,
    subject_choice_birth_date timestamp,
    subject_choice_gender varchar(255),
    thursday_first_period_begining varchar(255),
    thursday_first_period_ending varchar(255),
    thursday_period varchar(255),
    thursday_second_period_begining varchar(255),
    thursday_second_period_ending varchar(255),
    tuesday_first_period_begining varchar(255),
    tuesday_first_period_ending varchar(255),
    tuesday_period varchar(255),
    tuesday_second_period_begining varchar(255),
    tuesday_second_period_ending varchar(255),
    wednesday_first_period_begining varchar(255),
    wednesday_first_period_ending varchar(255),
    wednesday_period varchar(255),
    wednesday_second_period_begining varchar(255),
    wednesday_second_period_ending varchar(255),
    primary key (id)
);

create table child_care_center_registration_request_welcoming_choice (
    child_care_center_registration_request_id int8 not null,
    welcoming_choice_id int8 not null,
    welcoming_choice_index int4 not null,
    primary key (child_care_center_registration_request_id, welcoming_choice_index)
);


alter table child_care_center_registration_request_welcoming_choice 
    add constraint FK48474858C87E52FA 
    foreign key (child_care_center_registration_request_id) 
    references child_care_center_registration_request;

alter table child_care_center_registration_request_welcoming_choice 
    add constraint FK484748582B1979B1 
    foreign key (welcoming_choice_id) 
    references local_referential_data;

