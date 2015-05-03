(ns cdlib.artists.model
  (:require [korma.core :as db]
            [db.core]))

(defn fetch [name]
  (first (db/select :artists
                    (db/where {:name name}))))

(defn create [name user-id]
  (db/insert :artists
             (db/values {:name name
                         :user_id user-id})))

(defn update [id data]
  (db/update :artists
             (db/set-fields data)
             (db/where {:id id})))
