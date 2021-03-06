(ns scramblies.server
  (:require [scramblies.handler :refer [handle-scramble]])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :as response]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]])
  (:gen-class))


(defroutes routes
  (GET "/" [] (response/resource-response "public/index.html"))
  (GET "/api" [] handle-scramble)
  (not-found "<h1 align='center'>Page not found</h1>"))

(def app
  (wrap-reload
   (wrap-resource
    (wrap-params
     routes)
    "public")))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port)}))


(comment
  (defonce server (jetty/run-jetty #'app {:port 8000 :join? false}))

  (.start server)
  (.stop server))
