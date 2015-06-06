(ns cdlib.users.collection.by-artist
  (:require [cdlib.users.collection.model :as db]
            [cdlib.artists.model :as artists]
            [formant.validators :as validators]
            [formant.core :as formant]))

(defn- coerce-number
  "Coerces a value to an number otherwise returns nil."
  [val]
  (if-not (integer? val)
    (try
      (Long/parseLong val)
      (catch NumberFormatException e
        nil))
    val))

(defn- coerce-artist-id [data]
  (let [artist-id (coerce-number (get-in data [:data :artist-id]))]
    (assoc-in data [:data :artist-id] artist-id)))

(defn- get-collection [data]
  (let [user-id (get-in data [:data :user-id])
        artist-id (get-in data [:data :artist-id])]
    (assoc-in data [:data :discs] (db/get-user-artist-discs user-id artist-id))))

(defn- get-artist [data]
  (let [result (artists/find_by_id (get-in data [:data :artist-id]))]
    (println result)
    (assoc-in data [:data :artist] result)))

(defn- is-seq [data key]
  (assoc-in data [:data key] ()))

(def data-validators
  [[:discs is-seq]
   [:artist-id (validators/required)]
   [:user-id (validators/required)]])

(defn perform [params]
  (formant/validate params
                    data-validators
                    [coerce-artist-id get-collection get-artist]))
