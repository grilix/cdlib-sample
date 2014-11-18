(ns cdlib.services.get-user-collection
  (:require [cdlib.db.disc :as db]))

(defn perform [user-id]
  {:data (db/get-user-discs user-id)})
