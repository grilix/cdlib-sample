(ns cdlib.users.collection.list
  (:require [cdlib.users.collection.model :as db]
            [formant.validators :as validators]
            [formant.core :as formant]))

(defn- get-collection [data]
  (let [user-id (get-in data [:data :user-id])]
    (assoc data :data {:discs (db/get-user-discs user-id)})))

(defn- is-seq [data key]
  (assoc-in data [:data key] ()))

(def data-validators
  [[:discs is-seq]
   [:user-id (validators/required)]])

(defn perform [params]
  (formant/validate params
                    data-validators
                    [get-collection]))
