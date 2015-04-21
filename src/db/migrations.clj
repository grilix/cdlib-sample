(ns db.migrations
  (:require [db.core :as db]
            [ragtime.sql.files :as files]
            [ragtime.main :as ragtime]))

(defn migrate []
  (ragtime/migrate {:database (str "jdbc:" db/db-url)
                    :migrations (str "ragtime.sql.files/migrations")}))

(defn rollback []
  (ragtime/rollback {:database (str "jdbc:" db/db-url)
                    :migrations (str "ragtime.sql.files/migrations")}))
