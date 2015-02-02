alter table home_folder add column is_imported_and_not_initialized bool;
update home_folder set is_imported_and_not_initialized = false;
update home_folder set is_imported_and_not_initialized = true
    where id in (select individual_role.home_folder_id from adult, individual_role
                 where adult.id = individual_role.owner_id
                 and individual_role.role = 'HOME_FOLDER_RESPONSIBLE'
                 and adult.email = 'Changer@LeMail.fr');
