(ns cdlib.services.auth-user
  "Service that authenticates a user using :id and :pass"
  (:use [formar.core :refer [defform pattern required]])
  (:require [crypto.password.pbkdf2 :as password]
            [cdlib.db.user :as db]))

(defform validate
  [[[:id (required :message "Enter your username")]
    [:pass (required :message "Enter your password")]]])

(defn get-user [data]
  (if (empty? (data :data-errors))
    (if-let [account (db/get-user (get-in data [:data :id]))]
      (if (password/check (get-in data [:data :pass]) (account :pass))
        data
        (assoc-in data [:data-errors :pass] ["Invalid password"]))
      (assoc-in data [:data-errors :id] ["Invalid username"]))
    data))

(defn perform [params]
  (-> params validate get-user))
