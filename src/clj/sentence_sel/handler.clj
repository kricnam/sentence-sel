(ns sentence-sel.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [sentence-sel.layout :refer [error-page]]
            [sentence-sel.routes.home :refer [home-routes]]
            [sentence-sel.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [sentence-sel.env :refer [defaults]]
            [mount.core :as mount]
            [ring.middleware.cors :refer [wrap-cors]]
            [sentence-sel.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))



(def app-routes
  (routes
    (-> #'home-routes
        ;;(wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
