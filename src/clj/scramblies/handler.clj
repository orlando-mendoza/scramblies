(ns scramblies.handler
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn scramble? [s1 s2]
  (let [letters (frequencies s1)
        word (frequencies s2)]
    (every? (fn [[k v]] (<= v (get letters k 0))) word)))

(defn handle-scramble [req]
  (let [s1 (str (get-in req [:query-params "s1"]))
        s2 (str (get-in req [:query-params "s2"]))
        _ (println "s1: " s1)
        _ (println "s2: " s2)
        result (scramble? s1 s2)]
    {:headers {"Content-type" "application/json"}
     :status 200
     :body (json/write-str {:result result})}))

(comment
  (scramble? "ls cifa ;ks jkqa" "casa" )
          )
