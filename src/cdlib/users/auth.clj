(ns cdlib.users.auth
  "Service that authenticates a user using :id and :pass"
  (:require [crypto.password.pbkdf2 :as password]
            [cdlib.users.model :as db]
            [formant.core :as formant]
            [formant.validators :as validators]))

(def data-validators
  [[:id (validators/required :message "Enter your username")]
   [:pass (validators/required :message "Enter your password")]])

(defn- get-user [data]
  (if-let [account (db/get-user (get-in data [:data :id]))]
    (if-let [user-pass (get-in data [:data :pass])]
      (if (password/check user-pass (account :pass))
        data
        (assoc-in data [:data-errors :pass] ["Invalid password"]))
      (assoc-in data [:data-errors :id] ["Inactive account"]))
    (assoc-in data [:data-errors :id] ["Invalid username"])))

(def form-validators
  [get-user])

(defn perform [params]
  (formant/validate params data-validators form-validators))
