-- Casse sur les prénoms
UPDATE individual
set first_name = initcap(first_name)
where upper(first_name) <> 'A NAITRE';

-- Suppression des login inutiles

CREATE OR REPLACE FUNCTION suppressionlogininutile()
  RETURNS void AS
$BODY$
DECLARE  
  adults record;
  compteur bigint;
BEGIN
-- Suppresion des logins qui ne servent pas car attribués à un adulte non responsable de compte
--
-- Nombre de comptes à modifier
compteur := 0;
-- Liste des adultes
for adults in
	SELECT adult.id, individual.home_folder_id, adult.login
	FROM adult, individual
	WHERE adult.id not in (
		select adult.id
		from adult, individual_role
		where adult.id = individual_role.owner_id
		and individual_role.role = 'HOME_FOLDER_RESPONSIBLE'
	)
	and adult.id = individual.id
	and (
		adult.login notnull
		or
		adult.login = ''
	)
loop
	update adult set login = null where adult.id = adults.id;
	RAISE NOTICE 'Login supprimé - Id Compte :  : %', adults.home_folder_id;
	RAISE NOTICE 'Login supprimé - Id Adulte :  : %', adults.id;
	RAISE NOTICE 'Login supprimé - Login :  : %', adults.login;
	RAISE NOTICE '------------------------------------------';
	
compteur := compteur + 1;
end loop;

-- log
RAISE NOTICE 'Nombre de comptes à modifier : %', compteur;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION suppressionlogininutile()
  OWNER TO libredemat;
select  suppressionlogininutile();



-- Statut des individus

-- Changer la valeur de la date !

update individual
set state = 'VALID'
where state = 'MODIFIED'
and home_folder_id NOTnull
AND last_modification_date < '01/06/2014';


-- Modification du q_o_s

-- Changer la valeur de la date !

update individual
set q_o_s = null
where q_o_s = 'GOOD'
and home_folder_id NOTnull
AND last_modification_date < '01/06/2014';


-- Statut familial

update adult set family_status = 'SEPARATED' where family_status = 'Separate';
update adult set family_status = 'PACS' where family_status = 'Pacs';
