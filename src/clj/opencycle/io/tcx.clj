(ns opencycle.io.tcx
  (:require [clojure.data.xml :as xml]
            [clojure.zip :as zip]
            [clojure.data.zip.xml :refer [text xml-> xml1->]]
            [opencycle.geo :as geo]
            [opencycle.io.io :as io]
            [opencycle.models :as models])
  (:import (java.time ZonedDateTime)))

(defn parse-date-time [datetime]
  (-> datetime
      ZonedDateTime/parse
      .toInstant
      .getEpochSecond))

(defn parse-trackpoint [trackpoint]
  {:timestamp      (parse-date-time (xml1-> trackpoint :Time text))
   :latitude       (read-string (xml1-> trackpoint :Position :LatitudeDegrees text))
   :longitude      (read-string (xml1-> trackpoint :Position :LongitudeDegrees text))
   :elevation      (read-string (xml1-> trackpoint :AltitudeMeters text))
   :distance       (read-string (xml1-> trackpoint :DistanceMeters text))
   :heart-rate-bpm (read-string (xml1-> trackpoint :HeartRateBpm :Value text))})

(defn fix-zero-distances [trackpoints]
  (reduce (fn [coll trackpoint]
            (let [next-value (if (empty? coll)
                               trackpoint
                               (if (= 0.0 (:distance trackpoint))
                                 (assoc trackpoint :distance (:distance (last coll)))
                                 trackpoint))]
              (conj coll next-value)))
          []
          trackpoints))

(defn post-process [trackpoints]
  (-> trackpoints
      fix-zero-distances))

(defn parse [input-stream]
  (let [parsed-xml (xml/parse input-stream)
        zipped-xml (zip/xml-zip parsed-xml)
        zipped-trackpoints (xml-> zipped-xml
                                  :TrainingCenterDatabase
                                  :Activities
                                  :Activity
                                  :Lap
                                  :Track
                                  :Trackpoint)
        parsed-trackpoints (map parse-trackpoint zipped-trackpoints)]
    (post-process parsed-trackpoints)))





(def trackpoints (parse (io/input-stream-from-file "C:/Users/Anthony/Downloads/11368917461.tcx")))
(def sample-points (map #(models/make-sample (:timestamp %)
                                             (:latitude %)
                                             (:longitude %)
                                             (:elevation %)
                                             (:distance %)) trackpoints))

(def derived-sample-points (models/derive-sample-points sample-points))


