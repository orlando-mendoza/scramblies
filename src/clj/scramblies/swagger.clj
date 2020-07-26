(ns scramblies.swagger
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(s/defschema Result
  {:result s/Bool})

(s/defschema Request
  {:s1 s/Str
   :s2 s/Str})

(defn scramble? [s1 s2]
  (let [letters (frequencies s1)
        word (frequencies s2)]
    (every? (fn [[k v]] (<= v (get letters k 0))) word)))

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Scramblies"
                   :description "API for Scramblies Challenge"}
            :tags [{:name "api", :description "Scramblies challenge"}]}}}
   (context "/api" []
     :tags ["api"]

     (GET "/scramblies/:s1/:s2" []
       :path-params [s1 :- s/Str
                     s2 :- s/Str]
       :return Result
       :summary "A portion of :s1 can be rearranged to match :s2"
       (ok {:result (scramble? s1 s2)})))))