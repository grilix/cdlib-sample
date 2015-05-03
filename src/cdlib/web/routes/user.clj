(ns cdlib.web.routes.user
  (:require [cdlib.web.layout :as layout]
            [cdlib.users.get-profile :as get-user-profile]
            [cdlib.users.update-profile :as update-user-profile]
            [ring.util.response :as ring]))

(defn handle-profile [{session :session}]
  (let [result (get-user-profile/perform (session :user-id))]
    (if (empty? (result :data-errors))
      (layout/render "user/profile.html" (layout/default-params session result))
      (ring/redirect "/"))))

(defn handle-update-profile [{params :form-params
                              session :session}]
  (let [result (update-user-profile/perform params (session :user-id))]
    (if (empty? (result :data-errors))
      (ring/redirect-after-post "/profile")
      (layout/render "user/profile.html"
                     (layout/default-params session result)))))

(def user-routes
  {[:get "/profile"] handle-profile
   [:post "/update-profile"] handle-update-profile})
