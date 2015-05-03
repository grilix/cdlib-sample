CREATE TABLE users
(id varchar(20) primary key NOT NULL,
 first_name varchar(30),
 last_name varchar(30),
 email varchar(30) NOT NULL,
 admin boolean,
 last_login time,
 is_active boolean,
 pass varchar(100));
