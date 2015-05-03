(ns cdlib.web.layout
  (:require [selmer.parser :as parser]
            [selmer.filters :as filters]
            [ring.util.response :refer [content-type response]]))

(def template-path "templates/")

(defn default-params [session result]
  {:user-id (session :user-id)
  :csrf-token (session :__anti-forgery-token)
  :data (or (result :data) {})
  :errors (or (result :data-errors) {})})

(defn render [template & [params]]
  (let [body (parser/render-file (str template-path template) params)]
    (-> (response body)
      (content-type "text/html; charset=utf-8"))))
