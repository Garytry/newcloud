create user "APP_GATEWAY" identified by "password01";
grant "PUBLIC","DBA" to "APP_GATEWAY";
alter user "APP_GATEWAY" limit  failed_login_attemps unlimited, password_lock_time unlimited, password_grace_time unlimited;