(ns cdlib.users.discs.get-collection
  (:require [cdlib.users.discs.model :as db]))

(defn perform [user-id]
  {:data (db/get-user-discs user-id)})
