(ns scramblies.handler-test
  (:require [clojure.test :refer :all]
            [scramblies.handler :refer :all]
            [scramblies.server :refer :all]
            [ring.mock.request :as mock]))

(deftest a-test1
  (testing "scramble"
    (is (= true (scramble? "rkqodlw", "world")))
    (is (= true (scramble? "cedewaraaossoqqyt", "codewars")))
    (is (= false (scramble? "katas", "steak")))
    (is (= false (scramble? "scriptjavx", "javascript")))
    (is (= true (scramble? "scriptingjava", "javascript")))
    (is (= true (scramble? "scriptsjava", "javascripts")))
    (is (= false  (scramble? "javscripts", "javascript")))
    (is (= true (scramble? "aabbcamaomsccdd", "commas")))
    (is (= true (scramble? "commas", "commas")))
    (is (= true (scramble? "sammoc", "commas")))))

(deftest b-test1
  (testing "scramble?"
    (is (= true (scramble? "fdjpvopawknalsikj", "pink")))
    (is (= true (scramble? "okusfmnohylkd", "floyd")))
    (is (= false (scramble? "ceaossntroqqyt", "contract")))
    (is (= false (scramble? "ampfnqycogush", "dog")))
    (is (= false (scramble? "scriptjavx", "javascript")))
    (is (= true (scramble? "pyfahlktghoibasgn", "python")))
    (is (= true (scramble? "hirljkgac", "rick")))
    (is (= false (scramble? "inmutability", "immutability")))
    (is (= true (scramble? "fsjlafsdiadp", "lisp")))
    (is (= true (scramble? "repl", "repl")))))

(deftest c-test1
  (testing "handle-scramble"
    (let [_ (println (app (-> (mock/request :get "/api")
                                          (mock/query-string {:s1 "fahdfkhalskjblj"
                                                              :s2 "pink"}))))])
    (is (= (app (-> (mock/request :get "/api")
                                (mock/query-string {:s1 "fdjpvopawknalsikj"
                                                    :s2 "pink"})))
           {:headers {"Content-type" "application/json"}
            :status 200
            :body "{\"result\":true}"}))
    (is (= (app (-> (mock/request :get "/api")
                                (mock/query-string {:s1 "pyfahlktghoibasgn"
                                                    :s2 "python"})))
           {:headers {"Content-type" "application/json"}
            :status 200
            :body "{\"result\":true}"}))
    (is (= (app (-> (mock/request :get "/api")
                                (mock/query-string {:s1 "inmutability"
                                                    :s2 "immutability"})))
           {:headers {"Content-type" "application/json"}
            :status 200
            :body "{\"result\":false}"}))
    (is (= (app (-> (mock/request :get "/api")
                                (mock/query-string {:s1 "plis"
                                                    :s2 "lisp"})))
           {:headers {"Content-type" "application/json"}
            :status 200
            :body "{\"result\":true}"}))
    (is (= (app (-> (mock/request :get "/api")
                                (mock/query-string {:s1 "scriptjavx"
                                                    :s2 "javascript"})))
           {:headers {"Content-type" "application/json"}
            :status 200
            :body "{\"result\":false}"}))
    ))
