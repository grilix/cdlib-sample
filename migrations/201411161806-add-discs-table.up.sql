CREATE TABLE discs
(id serial primary key,
  user_id varchar(20) references users(id),
  title varchar(20),
  artist varchar(20),
  state varchar(12));
