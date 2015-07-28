delete from individual_mapping where individual_id IN (select id from individual where state='ARCHIVED');
