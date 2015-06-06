(ns cdlib.web.routes.artists
  (:require [cdlib.web.layout :as layout]
            [cdlib.users.collection.by-artist :as artist-collection]
            [ring.util.response :as ring]))

(defn- handle-show-artist [{session :session
                            params :params}]
  (let [params {"user-id" (session :user-id)
                "artist-id" (params :artist-id)}
        result (artist-collection/perform params)]
    (layout/render "artists/collection.html" (layout/default-params session result))))

(def artists-routes
  {[:get "/artists/:artist-id"] handle-show-artist}
  ;{[:get "/artists/:artist-id/new"] handle-add-artist-disc}
  )
