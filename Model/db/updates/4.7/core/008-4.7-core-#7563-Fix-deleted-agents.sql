-- Remove unknow references to Agent from CategoryRoles
DELETE FROM agent_category_roles WHERE agent_id NOT in (SELECT id FROM Agent)