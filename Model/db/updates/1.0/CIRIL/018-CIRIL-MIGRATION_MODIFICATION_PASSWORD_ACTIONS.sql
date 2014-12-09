update user_action set note = 'Courriel de ré-initialisation du mot de passe' where type = 'MODIFICATION_PASSWORD';
update user_action set type = 'NOTIFIED' where type = 'MODIFICATION_PASSWORD';
