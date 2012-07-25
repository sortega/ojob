(ns ojobs.core
  (:require [clojure.string :as s]))

(defn order-jobs [input]
  (first
    (s/split input #"\s*=>\s*")))
