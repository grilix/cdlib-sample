CREATE TABLE "users_collections"
("disc_id" integer references "discs"("id") NOT NULL,
 "user_id" varchar(20) references "users"("id") NOT NULL,
 "state" varchar(12) NOT NULL,
 CONSTRAINT "users_collections_pkey" PRIMARY KEY ("disc_id", "user_id"));
