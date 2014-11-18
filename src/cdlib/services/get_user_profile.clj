(ns cdlib.services.get-user-profile
  (:require [cdlib.db.user :as db]))

(defn perform [id]
  (if-let [account (db/get-user id)]
    {:data account :data-errors {}}
    {:data {} :data-errors {:id ["User does not exist"]}}))
