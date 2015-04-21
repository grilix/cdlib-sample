(ns cdlib.web.routes.auth
  (:require [cdlib.web.layout :as layout]
            [cdlib.users.auth :as auth-user]
            [cdlib.users.create :as create-user]
            [ring.util.response :as ring]))

(defn handle-register [{session :session}]
  (layout/render "registration.html" {:id ""
                                      :csrf-token (session :__anti-forgery-token)
                                      :errors {}}))

(defn handle-registration [{params :form-params
                            session :session}]
  (let [result (create-user/perform params)]
    (if (empty? (result :data-errors))
      (assoc (ring/redirect-after-post "/")
              :session {:user-id (get-in result [:data :id])})
      (layout/render
        "registration.html" {:id (params "id" "")
                             :csrf-token (session :__anti-forgery-token)
                             :errors (result :data-errors)}))))

(defn handle-login [{params :form-params}]
  (let [result (auth-user/perform params)]
    (if (empty? (result :data-errors))
      (assoc-in (ring/redirect-after-post "/")
             [:session :user-id] (get-in result [:data :id]))
      (ring/status (ring/response "ERROR") 401))))

(defn handle-logout [req]
  (assoc (ring/redirect "/") :session nil))

(def auth-routes
  {[:get "/register"] handle-register
   [:post "/register"] handle-registration
   [:post "/login"] handle-login
   [:get "/logout"] handle-logout}) ;todo: change logout to POST (or DELETE)
