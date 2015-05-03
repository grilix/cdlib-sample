(ns cdlib.users.create
  (:require [crypto.password.pbkdf2 :as password]
            [cdlib.users.model :as db]
            [formant.core :as formant]
            [formant.validators :as validators]))

(defn passwords-match [data]
  (let [pass (get-in data [:data :pass])
        pass1 (get-in data [:data :pass1])]
    (if-not (= pass pass1)
      (assoc-in data [:data-errors :pass1] "Passwords should match")
      data))
    data)

(defn unique-id [data]
  (if (db/get-user (get-in data [:data :id]))
    (assoc-in data [:data-errors :id] "Username already taken")
    data)
  data)

(def data-validators
  [[:id
    (validators/required  :message "Choose your name")
    (validators/pattern #"^[a-zA-Z0-9_]+$" :message "Has invalid characters")]
   [:email (validators/required :message "Enter your email")]
   [:pass (validators/required :message "Enter a password")]
   [:pass1 (validators/required :message "Confirm your password")]])

(defn create-user [data]
  (db/create-user {:id (get-in data [:data :id])
                  :email (get-in data [:data :email])
                  :pass (password/encrypt (get-in data [:data :pass]))})
  data)

(def form-validators
  [passwords-match
   unique-id
   create-user])

(defn perform [params]
  (formant/validate params data-validators form-validators))
