(ns ojobs.core-test
  (:use clojure.test
        midje.sweet
        ojobs.core)
  (:require [clojure.string :as s]))

(fact "no jobs"
      (order-jobs "") => "")

(fact "a single job"
      (order-jobs "a =>\n") => "a")

(fact "some jobs without dependencies"
      (set
        (order-jobs (s/join \newline ["a =>"
                                      "b =>"
                                      "c =>"]))) => (set "abc"))

