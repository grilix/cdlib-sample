(ns cdlib.services.update-user-profile
  (:use [formar.core :refer [defform email pattern required]])
  (:require [cdlib.db.user :as db]))

(defform validate
  [[[:email (email :message "Enter a valid email")]
    [:first-name (required :message "Enter your name")]
    [:last-name (required :message "Enter your last name")]]])

(defn update-user [data user-id]
  (if (empty? (data :data-errors))
    {:data (db/update-user user-id (data :data))
    :data-errors {}}
    data))

(defn perform [params user-id]
  (-> params
      validate
      (update-user user-id)))
