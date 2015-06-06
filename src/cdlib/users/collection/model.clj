(ns cdlib.users.collection.model
  (:use [clojure.set :refer [rename-keys map-invert]])
  (:require [korma.core :as db]
            [db.core]))

(defn add-disc [attrs]
  (db/insert :users_collections (db/values attrs)))

(defn get-user-discs [user-id]
  (db/select
    :users_collections
    (db/fields :users_collections.state :discs.title
               [:artists.name :artist_name]
               [:artists.id :artist_id])
    (db/join :discs (= :discs.id :users_collections.disc_id))
    (db/join :artists (= :artists.id :discs.artist_id))
    (db/where {:users_collections.user_id user-id})))

(defn get-user-artist-discs [user-id artist-id]
  (db/select
    :users_collections
    (db/fields :users_collections.state :discs.title
               [:artists.name :artist_name]
               [:artists.id :artist_id])
    (db/join :discs (= :discs.id :users_collections.disc_id))
    (db/join :artists (= :artists.id :discs.artist_id))
    (db/where {:users_collections.user_id user-id
               :artist_id artist-id})))
