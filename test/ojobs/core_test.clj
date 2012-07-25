(ns ojobs.core-test
  (:use clojure.test
        midje.sweet
        ojobs.core)
  (:require [clojure.string :as s]))

(defn precedence [a b]
  (fn [order]
    (< (.indexOf order a)
       (.indexOf order b))))

(defn lines [& elems]
  (s/join \newline elems))

(fact "no jobs"
      (order-jobs "") => "")

(fact "a single job"
      (order-jobs "a =>\n") => "a")

(fact "some jobs without dependencies"
      (set
        (order-jobs (lines "a =>"
                           "b =>"
                           "c =>"))) => (set "abc"))

(fact "Multiple Jobs, Single Dependency"
      (let [order (order-jobs (lines "a =>"
                                     "b => c"
                                     "c =>"))]
        (set order) => (set "abc")
        order => (precedence "c" "b")))

(fact "Multiple Jobs, Multiple Dependencies"
      (let [order (order-jobs (lines "a =>"
                                     "b => c"
                                     "c => f"
                                     "d => a"
                                     "e => b"
                                     "f =>"))]
        (set order) => (set "abcdef")
        order => (precedence "c" "b")
        order => (precedence "f" "c")
        order => (precedence "a" "d")
        order => (precedence "b" "e")))

(fact "Multiple Jobs, Self Referencing Dependency"
      (order-jobs (lines "a =>"
                         "b =>"
                         "c => c")) => (throws IllegalArgumentException
                                               "Error: self-reference"))

