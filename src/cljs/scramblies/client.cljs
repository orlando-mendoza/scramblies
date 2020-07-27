(ns scramblies.client
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.dom :as rd]
              [ajax.core :refer [GET]]))

(enable-console-print!)

(defonce app-state (atom {:title "Scramblies"
                          :letters ""
                          :word ""
                          :data-received? false
                          :result {:label "Result"
                                   :value nil}}))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text  :failure)))

(defn handle-response [resp]
  (let [_ (println resp)
        res (:result resp)]
    (swap! app-state
           update-in [:result :value] (constantly res))
    (println "app-state: " app-state)))

(defn get-result! []
  (let [s1 (:letters @app-state)
        s2 (:word @app-state)]
    (GET "/api" {:params {:s1 s1
                          :s2 s2}
                 :handler handle-response
                 :error-handler error-handler
                 :response-format :json
                 :keywords? true})))

(defn result [rslt]
  (println (str "rslt: " rslt))
  [:div {:class "result"}
   [:h2 (:label rslt)]
   [:div {:class "value"}
    (str (:value rslt))]])


(defn title []
  [:h1 (:title @app-state)])

(defn letters []
  [:div {:class-name "letters"}
   [:h3 "Enter letters"]
   [:input {:type "text"
            :placeholder "Letters"
            :value (:letters @app-state)
            :on-change #(swap! app-state assoc :letters (-> % .-target .-value))}]])

(defn word []
  [:div {:class-name "word"}
   [:h3 "Enter a word"]
   [:input {:type "text"
            :placeholder "Word"
            :value (:word @app-state)
            :on-change #(swap! app-state assoc :word (-> % .-target .-value))}]
   [:button {:on-click get-result!} "Try!"]])

(defn app []
  [:div {:class "app"}
   [title]
   [letters]
   [word]
   [result (:result @app-state)]])

(rd/render [app]
           (. js/document (getElementById "app")))