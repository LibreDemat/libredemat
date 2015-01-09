    create table conflans_municipal_waste_request (
        id int8 not null,
        composteur_grand bytea,
        composteur_petit bytea,
        nom_organisation varchar(255),
        nombre_residants varchar(255),
        om_cent_vingt_litres bytea,
        om_deux_cent_quarante_litres bytea,
        om_six_cent_soixante_litres bytea,
        om_trois_cent_quarante_litres bytea,
        precisions_reparation varchar(1024),
        profil_demandeur varchar(255),
        tri_cent_vingt_litres bytea,
        tri_deux_cent_quarante_litres bytea,
        tri_six_cent_soixante_litres bytea,
        tri_trois_cent_quarante_litres bytea,
        type_habitation varchar(255),
        verre_cent_vingt_litres bytea,
        verre_deux_cent_quarante_litres bytea,
        verre_trente_cinq_litres bytea,
        adresse_organisation_id int8,
        primary key (id)
    );

    create table conflans_municipal_waste_request_conteneur (
        conflans_municipal_waste_request_id int8 not null,
        conteneur_id int8 not null,
        conteneur_index int4 not null,
        primary key (conflans_municipal_waste_request_id, conteneur_index)
    );

    create table conflans_municipal_waste_request_quartier (
        conflans_municipal_waste_request_id int8 not null,
        quartier_id int8 not null,
        quartier_index int4 not null,
        primary key (conflans_municipal_waste_request_id, quartier_index)
    );

    alter table conflans_municipal_waste_request 
        add constraint FK49CC15BC79FCFCD 
        foreign key (adresse_organisation_id) 
        references address;

    alter table conflans_municipal_waste_request_conteneur 
        add constraint FK2465E5C483DA04E6 
        foreign key (conflans_municipal_waste_request_id) 
        references conflans_municipal_waste_request;

    alter table conflans_municipal_waste_request_conteneur 
        add constraint FK2465E5C4D3F8ADAB 
        foreign key (conteneur_id) 
        references local_referential_data;

    alter table conflans_municipal_waste_request_quartier 
        add constraint FK20F08FDA83DA04E6 
        foreign key (conflans_municipal_waste_request_id) 
        references conflans_municipal_waste_request;

    alter table conflans_municipal_waste_request_quartier 
        add constraint FK20F08FDA3AC38F7B 
        foreign key (quartier_id) 
        references local_referential_data;
