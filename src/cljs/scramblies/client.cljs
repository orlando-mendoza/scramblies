(ns scramblies.client
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.dom :as rd]
              [ajax.core :refer [GET]]))

(enable-console-print!)

(defonce app-state (atom {:title "Scramblies"
                          :description "Enter a string of lower case letters to see if they can be rearranged to match your word"
                          :letters ""
                          :word ""
                          :button "Try it!"
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
   [:h3 (:label rslt)]
   [:div {:class "value"}
    (str (:value rslt))]])

(defn title []
  [:h1 (:title @app-state)])

(defn description []
  [:p
   [:span (:description @app-state)]])

(defn letters []
  [:div
   [:input {:type "text"
            :placeholder "Letters"
            :value (:letters @app-state)
            :on-change #(swap! app-state assoc :letters (-> % .-target .-value))}]
   [:h2 "Enter letters"]])

(defn word []
  [:div
   [:input {:type "text"
            :placeholder "Word"
            :value (:word @app-state)
            :on-change #(swap! app-state assoc :word (-> % .-target .-value))}]
   [:h2 "Enter a word"]])

(defn button []
  [:button {:class "btn"
           :on-click get-result!} "Try it!"])

(defn app []
  [:div {:class "app"}
   [:div {:class "container"}
    [title]
    [description]
    [:div {:class "inputs"}
     [:div {:class "inside-inputs"}
      [letters]]
     [:div {:class "inside-inputs"}
      [word]]]
    [:p]
    [:div {:class "btn-area"}
     [:p
      [button]]]
    [:div {:class "value"}
     [result (:result @app-state)]]]])

(rd/render [app]
           (. js/document (getElementById "app")))