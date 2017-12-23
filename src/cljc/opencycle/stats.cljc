(ns opencycle.stats)

(defn mean [ns]
  (let [cnt (count ns)]
    (if (pos? cnt)
      (/ (apply + ns)
         cnt)
      0.0)))

(defn median [ns]
  (let [cnt (count ns)
        sorted (sort ns)
        mid (quot cnt 2)]
    (if (odd? cnt)
      (nth sorted mid)
      (mean [(nth sorted mid) (nth sorted (dec mid))]))))

(defn variance
  ([ns]
    (variance ns (mean ns)))
  ([ns ns-mean]
    (mean (map #(* (- % ns-mean) (- % ns-mean)) ns))))

(defn compute-statistics-for-field [sample-points field]
  (let [ns    (map #(get (merge % (:other-fields %)) field) sample-points)
        _min  (apply min ns)
        _max  (apply max ns)
        _mean (mean ns)]

    {:field-name (name field)
     :min         _min
     :max         _max
     :mean        _mean
     :median      (median ns)
     :range       (- _max _min)
     :variance    (variance ns _mean)}))

(defn compute-statistics-for-fields [sample-points fields]
  (map (partial compute-statistics-for-field sample-points) fields))

(defn compute-statistics [sample-points]
  (let [fields [:speed :elevation :elevation-gain :acceleration :grade]
        other-fields (keys (:other-fields (first sample-points)))]
    (compute-statistics-for-fields sample-points (concat fields other-fields))))
