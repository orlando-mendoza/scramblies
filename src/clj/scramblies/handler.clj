(ns scramblies.handler
  (:require [clojure.data.json :as json]
            [clojure.spec.alpha :as s])
  (:gen-class))

(s/def ::lowerCaseLetters
       (s/spec
        #(boolean (and (string? %) (re-find #"^[a-z]+$" %)))))

(defn scramble? [s1 s2]
  "returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false. Uses only lowercase letters. No punctuation or digits"
  (if (and (boolean (s/valid? ::lowerCaseLetters s1)) (boolean (s/valid? ::lowerCaseLetters s2)))
    (let [letters (frequencies s1)
          word (frequencies s2)]
      (every? (fn [[k v]] (<= v (get letters k 0))) word))
    false))

(defn handle-scramble [req]
  (let [s1 (str (get-in req [:query-params "s1"]))
        s2 (str (get-in req [:query-params "s2"]))
        result (scramble? s1 s2)]
    {:headers {"Content-type" "application/json"}
     :status 200
     :body (json/write-str {:result result})}))

(comment
  (scramble? "ls cifa ;ks jkqa" "casa" )
;; => false

  (re-find #"^[a-z]+$" "Casa")
  ;; => nil;; => "asa"

  (s/conform ::lowerCaseLetters "casa" )
;; => "casa"
  (s/valid? ::lowerCaseLetters "casa")
;; => true

  (scramble? "casa" "casa")
;; => true

  (scramble? "casa" "Casa")
;; => false
          )
