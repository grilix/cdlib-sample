(ns cdlib.users.model
  (:use [clojure.set :refer [rename-keys map-invert]])
  (:require [korma.core :as db]
            [db.core]))

(def fields-map
  {:first-name :first_name
   :last-name :last_name})

(db/defentity users
  (db/prepare (fn [data]
                (rename-keys data fields-map)))
  (db/transform (fn [data]
                  (rename-keys data (map-invert fields-map)))))

(defn create-user [user]
  (db/insert
    users
    (db/values user)))

(defn update-user [id data]
  (db/update
    users
    (db/set-fields data)
    (db/where {:id id})))

(defn get-user [id]
  (first
    (db/select
      users
      (db/where {:id id})
      (db/limit 1))))
