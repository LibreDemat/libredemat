    create table parking_permit_temporary_relocation_request (
        id int8 not null,
        acceptation_reglement_interieur bool,
        heure_end varchar(255),
        heure_start varchar(255),
        immatriculation varchar(255),
        largeur bytea,
        longeur bytea,
        marque varchar(255),
        periode_end timestamp,
        periode_start timestamp,
        tonnage bytea,
        volume bytea,
        payment_id int8,
        requester_address_id int8,
        primary key (id)
    );

    create table parking_permit_temporary_relocation_request_equipment_used (
        parking_permit_temporary_relocation_request_id int8 not null,
        equipment_used_id int8 not null,
        equipment_used_index int4 not null,
        primary key (parking_permit_temporary_relocation_request_id, equipment_used_index)
    );

    create table parking_permit_temporary_relocation_request_perform_choice (
        parking_permit_temporary_relocation_request_id int8 not null,
        perform_choice_id int8 not null,
        perform_choice_index int4 not null,
        primary key (parking_permit_temporary_relocation_request_id, perform_choice_index)
    );

    alter table parking_permit_temporary_relocation_request 
        add constraint FK826DD839DBF56A6 
        foreign key (requester_address_id) 
        references address;

    alter table parking_permit_temporary_relocation_request 
        add constraint FK826DD83FC859B01 
        foreign key (payment_id) 
        references payment;

    alter table parking_permit_temporary_relocation_request_equipment_used 
        add constraint FK1775820ADCF45D04 
        foreign key (equipment_used_id) 
        references local_referential_data;

    alter table parking_permit_temporary_relocation_request_equipment_used 
        add constraint FK1775820A801C7F07 
        foreign key (parking_permit_temporary_relocation_request_id) 
        references parking_permit_temporary_relocation_request;

    alter table parking_permit_temporary_relocation_request_perform_choice 
        add constraint FKC2280E7B1CD9AEF3 
        foreign key (perform_choice_id) 
        references local_referential_data;

    alter table parking_permit_temporary_relocation_request_perform_choice 
        add constraint FKC2280E7B801C7F07 
        foreign key (parking_permit_temporary_relocation_request_id) 
        references parking_permit_temporary_relocation_request;

