alter table home_folder add column is_imported_and_not_initialized bool;
update home_folder set is_imported_and_not_initialized = false;
