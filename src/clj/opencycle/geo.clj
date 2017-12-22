(ns opencycle.geo)

(def radius-of-earth-km 6371)
(def radius-of-earth-m (* 1000 radius-of-earth-km))

(defn haversine [lat1 lon1 lat2 lon2]
  "https://www.movable-type.co.uk/scripts/latlong.html"
  (let [lat1-rads (Math/toRadians lat1)
        lat2-rads (Math/toRadians lat2)
        dlat (Math/toRadians (- lat2 lat1))
        dlon (Math/toRadians (- lon2 lon1))
        a (+ (* (Math/sin (/ dlat 2))
                (Math/sin (/ dlat 2)))
             (* (Math/cos lat1-rads)
                (Math/cos lat2-rads)
                (Math/sin (/ dlon 2))
                (Math/sin (/ dlon 2))))
        c (* 2 (Math/atan2 (Math/sqrt a) (Math/sqrt (- 1 a))))]
    (* radius-of-earth-m c)))

(defn vincenty [lat1 lon1 lat2 lon2 convergence-factor max-iterations]
  "https://www.movable-type.co.uk/scripts/latlong-vincenty.html"
  (let [a 6378137.0
        f (/ 1 298.257223563)
        b (6356752.314245)
        lon1' (if (= lon1 -180) 180 lon1)
        φ1 (Math/toRadians lat1)
        λ1 (Math/toRadians lon1')
        φ2 (Math/toRadians lat2)
        λ2 (Math/toRadians lon2)
        L (- λ2 λ1)
        tanU1 (* (- 1 f) (Math/tan φ1))
        cosU1 (/ 1 (Math/sqrt (+ (1 (* tanU1 tanU1)))))
        sinU1 (* tanU1 cosU1)
        tanU2 (* (- 1 f) (Math/tan φ2))
        cosU2 (/ 1 (Math/sqrt (+ (1 (* tanU2 tanU2)))))
        sinU2 (* tanU2 cosU2)]
    (loop [λ L
           λ' 0.0
           iteration 0]
      (let [sinλ (Math/sin λ)
            cosλ (Math/cos λ)
            sinSqσ (+ (* (* cosU2 sinλ)
                         (* cosU2 sinλ))
                      (* (- (* cosU1 sinU1)
                            (* sinU1 cosU2 cosλ))
                         (- (* cosU1 sinU2)
                            (* sinU1 cosU2 cosλ))))]))))