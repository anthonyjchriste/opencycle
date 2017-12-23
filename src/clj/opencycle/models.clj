(ns opencycle.models)


(defn pairs [coll]
  (map vector coll (rest coll)))

(defn diff [ns]
  (map - (rest ns) ns))

(defn zero-divide [n d]
  (if (or (zero? n)
          (zero? d))
    0.0
    (/ n d)))

(defn pad-left [coll to-size]
  (if (= (count coll) to-size)
    coll
    (recur (into [0.0] coll) to-size)))

(defn sample-pair-difference [sample-pair key]
  (let [sp1 (first sample-pair)
        sp2 (second sample-pair)
        v1 (get sp1 key)
        v2 (get sp2 key)]
    (- v2 v1)))

(defn delta-ratio [sample-pair key1 key2]
  (let [delta-key1 (sample-pair-difference sample-pair key1)
        delta-key2 (sample-pair-difference sample-pair key2)]
    (zero-divide delta-key1 delta-key2)))

(defn derive-elevation-gain [sample-pair]
  (delta-ratio sample-pair :elevation :timestamp))

(defn derive-speed [sample-pair]
  (delta-ratio sample-pair :distance :timestamp))

(defn derive-grade [sample-pair]
  (delta-ratio sample-pair :elevation :distance))

(defn derive-acceleration [delta-speed delta-time]
  (zero-divide delta-speed delta-time))

(defn derive-accelerations [speeds timestamps]
  (let [delta-speeds (diff speeds)
        delta-timestamps (diff timestamps)]
    (map derive-acceleration speeds timestamps)))

(defn assoc-derived [samples derived key]
  (map (fn [sample v]
         (assoc sample key v))
       samples
       derived))

(defn derive-sample-points [samples]
  (let [sample-pairs (pairs samples)
        cnt (count samples)
        speeds (pad-left (map derive-speed sample-pairs) cnt)
        elevation-gains (pad-left (map derive-elevation-gain sample-pairs) cnt)
        grades (pad-left (map derive-grade sample-pairs) cnt)
        timestamps (map :timestamp samples)
        accelerations (pad-left (derive-accelerations speeds timestamps) cnt)
        total-times (pad-left (reduce
                                (fn [coll v]
                                  (conj coll (+ (last coll) v)))
                                [0.0]
                                (diff timestamps)) cnt)]
    (-> samples
        (assoc-derived speeds :speed)
        (assoc-derived elevation-gains :elevation-gain)
        (assoc-derived grades :grade)
        (assoc-derived accelerations :acceleration)
        (assoc-derived total-times :total-time))))

(defn make-sample [timestamp latitude longitude elevation distance]
  {:timestamp timestamp
   :latitude  latitude
   :longitude longitude
   :elevation elevation
   :distance  distance})
