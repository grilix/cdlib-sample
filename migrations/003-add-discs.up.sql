CREATE TABLE "discs"
("id" serial primary key,
 "user_id" varchar(20) references "users"("id") NOT NULL,
 "artist_id" integer references "artists"("id") NOT NULL,
 "title" varchar(40) NOT NULL);
