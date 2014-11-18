(ns cdlib.handler
  (:use nav.core)
  (:require [cdlib.routes.home :refer [home-routes]]
            [cdlib.routes.auth :refer [auth-routes]]
            [cdlib.routes.user :refer [user-routes]]
            [cdlib.routes.discs :refer [discs-routes]]
            [ring.middleware.session.cookie :as cookie]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace-web]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.util.response :as ring]))

(def app-routes
  (merge
    home-routes
    user-routes
    discs-routes
    auth-routes))

(def session-defaults
  {:cookie-attrs {:max-age 3600}
   :store (cookie/cookie-store {:key "ffffffffffcccccc"})}) ;todo: change the key :)

(def app
  (-> (combine-routes app-routes)
      wrap-anti-forgery
      wrap-params
      (wrap-session session-defaults)
      (wrap-resource "public")))
