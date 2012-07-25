(ns ojobs.core-test
  (:use clojure.test
        midje.sweet
        ojobs.core)
  (:require [clojure.string :as s]))

(defn precedence [a b]
  (fn [order]
    (< (.indexOf order a)
       (.indexOf order b))))

(fact "no jobs"
      (order-jobs "") => "")

(fact "a single job"
      (order-jobs "a =>\n") => "a")

(fact "some jobs without dependencies"
      (set
        (order-jobs (s/join \newline ["a =>"
                                      "b =>"
                                      "c =>"]))) => (set "abc"))

(fact "Multiple Jobs, Single Dependency"
      (let [order (order-jobs (s/join \newline ["a =>"
                                                "b => c"
                                                "c =>"]))]
        (set order) => (set "abc")
        order => (precedence "c" "b")))
