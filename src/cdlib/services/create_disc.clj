(ns cdlib.services.create-disc
  (:use [formar.core :refer [defform pattern required choice]])
  (:require [cdlib.db.disc :as db]))

(defform validate
  [[[:title (required :message "Title is required")]
    [:state (choice #{"want" "dont-want" "have"}
                    :msg-fn (constantly "Is invalid"))]
    [:artist (required :message "Artist is required")]]])

(defn create-disc [data user-id]
  (if (empty? (data :data-errors))
    (db/create-disc (assoc (data :data)
                           :user-id user-id))
    data))

(defn perform [params user-id]
  (-> params
      validate
      (create-disc user-id)))
