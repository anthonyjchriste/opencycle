(ns opencycle.conversions)

(defn meters->ft [meters]
  (* 3.28084 meters))

(defn meters->miles [meters]
  (* 0.000621371 meters))

(defn meters-per-seconds->miles-per-hour [meters-per-second]
  (* 2.23694 meters-per-second))

(defn seconds->minutes [seconds]
  (/ seconds 60.0))