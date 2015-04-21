(ns cdlib.web.routes.home
  (:require [cdlib.web.layout :as layout]))

(defn home-page [{session :session}]
  (layout/render
    "home.html" {:user-id (session :user-id)
                 :csrf-token (session :__anti-forgery-token)}))

(defn about-page [{session :session}]
  (layout/render "about.html" {:user-id (session :user-id)}))

(def home-routes
  {[:get "/"] home-page
   [:get "/about"] about-page})
