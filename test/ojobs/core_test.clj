(ns ojobs.core-test
  (:use clojure.test
        midje.sweet
        ojobs.core))

(fact "no jobs"
      (order-jobs "") => "")

(fact "a single job"
      (order-jobs "a =>\n") => "a")
