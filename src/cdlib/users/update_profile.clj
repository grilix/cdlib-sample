(ns cdlib.users.update-profile
  (:require [cdlib.users.model :as db]
            [formant.core :as formant]
            [formant.validators :as validators]))

(def data-validators
  [[:email (validators/email :message "Enter a valid email")]
   [:first-name (validators/required :message "Enter your name")]
   [:last-name (validators/required :message "Enter your last name")]])

(defn- update-user [user-id]
  (fn [data]
    (db/update-user user-id (data :data))
    data))

(defn perform [params user-id]
  (formant/validate params
                    data-validators
                    [(update-user user-id)]))
