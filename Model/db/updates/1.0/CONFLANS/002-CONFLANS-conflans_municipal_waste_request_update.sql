alter table conflans_municipal_waste_request add column composteur_grand bytea;
alter table conflans_municipal_waste_request add column composteur_petit bytea;
alter table conflans_municipal_waste_request add column om_cent_vingt_litres bytea;
alter table conflans_municipal_waste_request add column om_deux_cent_quarante_litres bytea;
alter table conflans_municipal_waste_request add column om_six_cent_soixante_litres bytea;
alter table conflans_municipal_waste_request add column om_trois_cent_quarante_litres bytea;
alter table conflans_municipal_waste_request add column tri_cent_vingt_litres bytea;
alter table conflans_municipal_waste_request add column tri_deux_cent_quarante_litres bytea;
alter table conflans_municipal_waste_request add column tri_six_cent_soixante_litres bytea;
alter table conflans_municipal_waste_request add column tri_trois_cent_quarante_litres bytea;
alter table conflans_municipal_waste_request add column verre_cent_vingt_litres bytea;
alter table conflans_municipal_waste_request add column verre_deux_cent_quarante_litres bytea;
alter table conflans_municipal_waste_request add column verre_trente_cinq_litres bytea;
alter table conflans_municipal_waste_request alter column nombre_residants type varchar(255);

create table conflans_municipal_waste_request_quartier (
    conflans_municipal_waste_request_id int8 not null,
    quartier_id int8 not null,
    quartier_index int4 not null,
    primary key (conflans_municipal_waste_request_id, quartier_index)
);

alter table conflans_municipal_waste_request_quartier 
    add constraint FK20F08FDA25286E05 
    foreign key (conflans_municipal_waste_request_id) 
    references conflans_municipal_waste_request;

alter table conflans_municipal_waste_request_quartier 
    add constraint FK20F08FDA6AB1B97C 
    foreign key (quartier_id) 
    references local_referential_data;