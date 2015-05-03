(ns cdlib.web.routes.home
  (:require [cdlib.web.layout :as layout]))

(defn home-page [{session :session}]
  (layout/render "home.html" (layout/default-params session {})))

(defn about-page [{session :session}]
  (layout/render "about.html" (layout/default-params session {})))

(def home-routes
  {[:get "/"] home-page
   [:get "/about"] about-page})
