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

(defn handle-response [resp]
  (let [result (get :result resp)]
    (swap! app-state
           update-in [:result :value] (constantly result))))

(defn get-result! []
  (let [s1 (:letters @app-state)
        s2 (:word @app-state)]
    (GET "/api" {:params {"s1" s1
                          "s2" s2}
                 :handler handle-response})))

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
   [word]])

(rd/render [app]
           (. js/document (getElementById "app")))

