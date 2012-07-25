(ns ojobs.core-test
  (:use clojure.test
        midje.sweet
        ojobs.core))

(fact "no jobs"
      (order-jobs "") => "")
