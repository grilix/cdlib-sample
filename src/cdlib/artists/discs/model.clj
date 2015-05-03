(ns cdlib.artists.discs.model
  (:require [korma.core :as db]
            [db.core]))

(defn fetch [attrs]
  (first (db/select :discs (db/where attrs))))

(defn create [data]
  (db/insert :discs
             (db/values data)))

(defn update [id data]
  (db/update :discs
             (db/set-fields data)
             (db/where {:id id})))
