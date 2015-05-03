(ns cdlib.web.routes.discs
  (:require [cdlib.web.layout :as layout]
            [cdlib.users.collection.add :as create-disc]
            [cdlib.users.collection.create-mass :as create-mass-discs]
            [cdlib.users.collection.list :as get-user-collection]
            [ring.util.response :as ring]))

(defn- handle-show-library [{session :session}]
  (let [params {"user-id" (session :user-id)}
        result (get-user-collection/perform params)]
    (layout/render "discs/index.html" (layout/default-params session result))))

(defn- handle-show-add-disc [{session :session}]
  (layout/render "discs/new.html" (layout/default-params session {})))

(defn- handle-show-mass [{session :session}]
  (layout/render "discs/mass-add.html" (layout/default-params session {})))

(defn- handle-mass-add [req]
  (let [params (req :params)
        session (req :session)
        result (create-mass-discs/perform params (session :user-id))]
    (if (empty? (result :data-errors))
      (ring/redirect-after-post "/discs")
      (layout/render "discs/mass-add.html"
                     (layout/default-params session result)))))

(defn- handle-add-disc [req]
  (let [params (req :params)
        session (req :session)
        result (create-disc/perform params (session :user-id))]
    (if (empty? (result :data-errors))
      (ring/redirect-after-post "/discs")
      (layout/render "discs/new.html"
                     (layout/default-params session result)))))

(def discs-routes
  {[:get "/discs"] handle-show-library
   [:get "/discs/new"] handle-show-add-disc
   [:get "/discs/mass"] handle-show-mass
   [:post "/discs/mass"] handle-mass-add
   [:post "/discs"] handle-add-disc})
