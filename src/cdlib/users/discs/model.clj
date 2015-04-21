(ns cdlib.users.discs.model
  (:use [clojure.set :refer [rename-keys map-invert]])
  (:require [korma.core :as db]
            [db.core]))

(def fields-map
  {:user-id :user_id})

(db/defentity discs
  (db/prepare (fn [data]
                (rename-keys data fields-map)))

  (db/transform (fn [data]
                  (rename-keys data (map-invert fields-map)))))

(defn create-disc [data]
  (db/insert discs
          (db/values data)))

(defn update-disc [id data]
  (db/update
    discs
    (db/set-fields data)
    (db/where {:id id})))

(defn get-user-discs [user-id]
  (db/select
    discs
    (db/where {:user_id user-id})))

