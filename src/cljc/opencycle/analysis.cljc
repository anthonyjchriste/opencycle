(ns opencycle.analysis)

(defn mean [coll]
  (let [sum (apply + coll)
        cnt (count coll)]
    (if (pos? cnt)
      (/ sum cnt)
      0)))