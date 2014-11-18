(ns cdlib.services.create-user
  (:use [formar.core :refer [defform pattern required]])
  (:require [crypto.password.pbkdf2 :as password]
            [cdlib.db.user :as db]))

(defform validate
  [[[:id
     (required  :message "Choose your name")
     (pattern #"^[a-zA-Z0-9_]+$" :message "Has invalid characters")]
    [:pass (required :message "Enter a password")]
    [:pass1 (required :message "Confirm your password")]]])

(defn passwords-match [data]
  (if (empty? (data :data-errors))
    (let [pass (get-in data [:data :pass])
          pass1 (get-in data [:data :pass1])]
      (if-not (= pass pass1)
        (assoc-in data [:data-errors :pass1] "Passwords don't match")
        data))
    data))

(defn unique-id [data]
  (if (empty? (data :data-errors))
    (if (db/get-user (get-in data [:data :id]))
      (assoc-in data [:data-errors :id] "Username already taken")
      data)
    data))

(defn create-user [data]
  (if (empty? (data :data-errors))
    (try
      (db/create-user {:id (get-in data [:data :id])
                      :pass (password/encrypt (get-in data [:data :pass]))})
      data
      (catch Exception ex
        (assoc-in data :form-errors [(.getMessage ex)])))
    data))

(defn perform [params]
  (-> params
      validate
      passwords-match
      unique-id
      create-user))
