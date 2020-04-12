create table users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(50) not null,
	enabled boolean not null
);

create table authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

--
-- ### MySQL DB Scritps ### --
-- ###Ideal
-- CREATE  TABLE users (  username VARCHAR(50) NOT NULL ,  password VARCHAR(50) NOT NULL ,
--  	enabled TINYINT NOT NULL DEFAULT 1 ,  PRIMARY KEY (username));
-- CREATE TABLE authorities (username VARCHAR(50) NOT NULL ,	authority VARCHAR(50) NOT NULL ,
--		constraint fk_authorities_users foreign key(username) references users(username));
-- CREATE unique index ix_auth_username on authorities (username,authority);

-- ###Actual MySQL
-- CREATE  TABLE user (	id INTEGER NOT NULL,	active TINYINT NOT NULL DEFAULT 1,
-- password VARCHAR(50) NOT NULL ,	roles VARCHAR(50) NOT NULL ,    user_name VARCHAR(50) NOT NULL ,
-- PRIMARY KEY (id));


