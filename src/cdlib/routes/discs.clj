(ns cdlib.routes.discs
  (:require [cdlib.layout :as layout]
            [cdlib.services.create-disc :as create-disc]
            [cdlib.services.get-user-collection :as get-user-collection]
            [ring.util.response :as ring]))

(defn handle-show-library [{session :session}]
  (let [result (get-user-collection/perform (session :user-id))]
    (layout/render "discs/index.html" {:collection (result :data)
                                       :user-id (session :user-id)})))

(defn handle-show-add-disc [{session :session}]
  (layout/render "discs/new.html" {:disc {}
                                   :user-id (session :user-id)
                                   :csrf-token (session :__anti-forgery-token)
                                   :errors {}}))

(defn handle-add-disc [req]
  (let [params (req :params)
        session (req :session)
        result (create-disc/perform params (session :user-id))]
    (if (empty? (result :data-errors))
      (ring/redirect-after-post "/discs")
      (layout/render
        "discs/new.html" {:disc (result :data)
                          :user-id (session :user-id)
                          :csrf-token (session :__anti-forgery-token)
                          :errors (result :data-errors)}))))

(def discs-routes
  {[:get "/discs"] handle-show-library
   [:get "/discs/new"] handle-show-add-disc
   [:post "/discs"] handle-add-disc})
