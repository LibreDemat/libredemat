create table park_card_request (
    id int8 not null,
    card_number_limit bytea,
    card_one_price numeric(19, 2),
    card_three_price numeric(19, 2),
    card_two_price numeric(19, 2),
    information_card_limit_rest varchar(255),
    park_resident varchar(255),
    payment_reference varchar(255),
    payment_total varchar(255),
    subject_address_id int8,
    primary key (id)
);

create table park_immatriculation (
    id int8 not null,
    immatriculation varchar(255),
    tarif varchar(255),
    park_card_request_id int8,
    park_imatriculation_index int4,
    primary key (id)
);


create table street_border_referential (
    id int8 not null,
    city varchar(255),
    postal_code varchar(255),
    street_label varchar(255),
    primary key (id)
);

alter table park_card_request 
    add constraint FK9647369558B2AF6 
    foreign key (subject_address_id) 
    references address;

alter table park_immatriculation 
    add constraint FKDF88749972930EBE 
    foreign key (park_card_request_id) 
    references park_card_request;

