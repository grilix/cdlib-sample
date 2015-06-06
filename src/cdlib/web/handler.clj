(ns cdlib.web.handler
  (:use nav.core)
  (:require [cdlib.web.routes.home :refer [home-routes]]
            [cdlib.web.routes.auth :refer [auth-routes]]
            [cdlib.web.routes.user :refer [user-routes]]
            [cdlib.web.routes.discs :refer [discs-routes]]
            [cdlib.web.routes.artists :refer [artists-routes]]
            [ring.middleware.session.cookie :as cookie]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace-web]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.util.response :as ring]))

(defn wrap-auth [handler options]
  (fn [request]
    (handler request)))

(def app-routes
  (merge
    home-routes
    user-routes
    discs-routes
    artists-routes
    auth-routes))

(def session-defaults
  {:cookie-attrs {:max-age 10000}
   :store (cookie/cookie-store {:key "ffffffffffcccccc"})}) ;todo: change the key :)

(def app
  (-> (combine-routes app-routes)
      (wrap-auth {})
      wrap-anti-forgery
      wrap-params
      (wrap-session session-defaults)
      (wrap-resource "public")))
