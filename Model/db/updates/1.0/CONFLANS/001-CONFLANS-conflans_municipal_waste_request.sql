create table conflans_municipal_waste_request (
    id int8 not null,
    nom_organisation varchar(255),
    nombre_residants varchar(2),
    precisions_reparation varchar(1024),
    profil_demandeur varchar(255),
    type_habitation varchar(255),
    adresse_organisation_id int8,
    primary key (id)
);

create table conflans_municipal_waste_request_conteneur (
    conflans_municipal_waste_request_id int8 not null,
    conteneur_id int8 not null,
    conteneur_index int4 not null,
    primary key (conflans_municipal_waste_request_id, conteneur_index)
);

alter table conflans_municipal_waste_request 
    add constraint FK49CC15BC89CD03EC 
    foreign key (adresse_organisation_id) 
    references address;

alter table conflans_municipal_waste_request_conteneur 
    add constraint FK2465E5C425286E05 
    foreign key (conflans_municipal_waste_request_id) 
    references conflans_municipal_waste_request;

alter table conflans_municipal_waste_request_conteneur 
    add constraint FK2465E5C43E6D7AC 
    foreign key (conteneur_id) 
    references local_referential_data;