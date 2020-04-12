INSERT INTO users(username, password, enabled)
values ('user', 'pass', true);

INSERT INTO users(username, password, enabled)
values ('admin', 'pass', true);

INSERT INTO authorities (username, authority)
values ('user', 'ROLE_USER');

INSERT INTO authorities (username, authority)
values ('admin', 'ROLE_ADMIN');

--
-- MySQL Data
--
-- INSERT INTO user(id, active, password, roles, user_name) values (1, true, 'pass', 'ROLE_USER', 'user');
-- INSERT INTO user(id, active, password, roles, user_name) values (2, true, 'pass', 'ROLE_ADMIN', 'admin');