(ns cdlib.users.collection.add
  (:require [cdlib.users.collection.model :as collection]
            [cdlib.artists.model :as artists]
            [cdlib.artists.discs.model :as discs]
            [formant.core :as formant]
            [formant.validators :as validators]))

(def data-validators
  [[:title (validators/required :message "Title is required")]
   [:state (validators/choice #{"want" "dont-want" "have"}
                              :msg-fn (constantly "Is invalid"))]
   [:artist (validators/required :message "Artist is required")]])

(defn- get-artist [user-id]
  (fn [params]
    (let [artist (or (artists/fetch ((params :data) :artist))
                     (artists/create ((params :data) :artist) user-id))]
      (assoc-in params [:data :artist-id] (artist :id)))))

(defn- get-disc [user-id]
  (fn [params]
    (let [data (params :data)
          disc-data {:artist_id (data :artist-id)
                     :title (data :title)}]
      (let [disc (or (discs/fetch disc-data)
                     (discs/create (assoc disc-data :user_id user-id)))]
        (assoc-in params [:data :disc-id] (disc :id))))))

(defn- add-disc [user-id]
  (fn [params]
    (let [disc (params :data)]
      (collection/add-disc {:user_id user-id
                            :disc_id (disc :disc-id)
                            :state (disc :state)})
      params)))

(defn- form-validators [user-id]
  [(get-artist user-id)
   (get-disc user-id)
   (add-disc user-id)])

(defn perform [params user-id]
  (formant/validate params
                    data-validators
                    (form-validators user-id)))
