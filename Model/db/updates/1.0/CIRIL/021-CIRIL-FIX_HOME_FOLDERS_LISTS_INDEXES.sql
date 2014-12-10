-- Add an int hibernate list index
CREATE FUNCTION init_hibernate_list_index(table_name text, foreign_id_col_name text, id_col_name text, index_col_name text) RETURNS void AS $$
 DECLARE
    current_index integer := 0;
    current_foreign_id integer := -1;
    current_rec record;
    select_cmd text := 'SELECT ' || quote_ident(id_col_name) || ' AS id, '
                       || quote_ident(foreign_id_col_name) || ' AS foreign_id'
                       || ' FROM ' || quote_ident(table_name)
                       || ' ORDER BY ' || quote_ident(foreign_id_col_name) || ', ' || quote_ident(id_col_name);
    update_cmd text ;
  BEGIN
    FOR current_rec IN EXECUTE select_cmd LOOP
      IF current_foreign_id <> current_rec.foreign_id THEN
        current_foreign_id := current_rec.foreign_id;
        current_index := 0;
      ELSE
        current_index := current_index + 1;
      END IF;
 
      update_cmd := 'UPDATE ' || quote_ident(table_name)
                    || ' SET ' || quote_ident(index_col_name) || ' = ' || current_index
                    || ' WHERE ' || quote_ident(id_col_name) || ' = ' || current_rec.id;
      EXECUTE update_cmd;
           
    END LOOP;
    RETURN;
  END;
$$ LANGUAGE plpgsql;

ALTER TABLE user_action DROP constraint if exists "home_folder_index_key";
select init_hibernate_list_index('user_action', 'home_folder_id', 'id', 'home_folder_index');
ALTER TABLE user_action ADD constraint "home_folder_index_key" unique (home_folder_id, home_folder_index);

ALTER TABLE individual DROP constraint if exists "home_folder_index_key";
select init_hibernate_list_index('individual', 'home_folder_id', 'id', 'home_folder_index');
ALTER TABLE individual ADD constraint "home_folder_index_key" unique (home_folder_id, home_folder_index);

DROP function init_hibernate_list_index(text,text,text,text);
