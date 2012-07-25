(ns ojobs.core
  (:require [clojure.string :as s]))

(defn parse-line [line]
  (first
    (s/split line #"\s*=>\s*")))

(defn order-jobs [input]
  (->> input
    s/split-lines
    (map parse-line)
    (apply str)))
