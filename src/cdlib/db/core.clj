(ns cdlib.db.core
  (:use korma.db)
  (:require [clojure.string :as str]
            [ring.util.codec :as codec])
  (:import java.net.URI))

(def db-url (get (System/getenv) "DATABASE_URL"))
(def db-values (URI. (.getSchemeSpecificPart (URI. db-url))))
(def db-url-query (codec/form-decode (.getQuery db-values)))

(def db-data
  {:host (.getHost db-values)
   :db (subs (.getPath db-values) 1)
   :user (get db-url-query "user")
   :password (get db-url-query "password")})

(def db (postgres db-data))

(defdb korma-db db)
