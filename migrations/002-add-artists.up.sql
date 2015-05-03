CREATE TABLE "artists" ("id" serial primary key,
 "user_id" varchar(20) references "users"("id") NOT NULL,
 "name" varchar(40) NOT NULL);
