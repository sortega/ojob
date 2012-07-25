(ns ojobs.core
  (:require [clojure.string :as s]))

(defn parse-line [line]
  (let [[task & others] (s/split line #"\s*=>\s*")
        deps            (set others)]
    (if (get deps task)
      (throw (IllegalArgumentException. "Error: self-reference"))
      [task deps])))

(defn independent-jobs [dependencies]
  (->> dependencies
    (filter (comp empty? second))
    (map first)))

(defn remove-jobs [dependencies jobs]
  (let [not-removed? (complement (set jobs))]
    (->> dependencies
      (keep (fn [[job deps]]
              (when (not-removed? job)
                [job (filter not-removed? deps)])))
      (into {}))))

(defn order-seq [dependencies]
  (lazy-seq
    (when-let [next-jobs (seq (independent-jobs dependencies))]
      (cons next-jobs
            (order-seq (remove-jobs dependencies next-jobs))))))

(defn order-jobs [input]
  (let [lines        (s/split-lines input)
        dependencies (into {} (map parse-line lines))
        order        (flatten (order-seq dependencies))]
    (apply str order)))
