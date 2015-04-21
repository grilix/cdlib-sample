(ns cdlib.users.discs.create
  (:require [cdlib.users.discs.model :as db]
            [formant.core :as formant]
            [formant.validators :as validators]))

(def data-validators
  [[:title (validators/required :message "Title is required")]
   [:state (validators/choice #{"want" "dont-want" "have"}
                              :msg-fn (constantly "Is invalid"))]
   [:artist (validators/required :message "Artist is required")]])

(defn create-disc [user-id]
  (fn [data]
    (assoc data
           :data (db/create-disc (assoc (data :data) :user-id user-id)))))

(defn form-validators [user-id]
  [(create-disc user-id)])

(defn perform [params user-id]
  (formant/validate params
                    data-validators
                    (form-validators user-id)))
