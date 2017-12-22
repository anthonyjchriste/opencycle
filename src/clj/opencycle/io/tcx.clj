(ns opencycle.io.tcx
  (:require [clojure.data.xml :as xml]
            [clojure.zip :as zip]
            [clojure.data.zip.xml :refer [text xml-> xml1->]]
            [opencycle.geo :as geo]
            [opencycle.io.io :as io])
  (:import (java.time ZonedDateTime)))

(defn parse-date-time [datetime]
  (-> datetime
      ZonedDateTime/parse
      .toInstant
      .getEpochSecond))

(defn parse-trackpoint [trackpoint]
  {:timestamp-epoch-seconds     (parse-date-time (xml1-> trackpoint :Time text))
   :latitude-degrees            (read-string (xml1-> trackpoint :Position :LatitudeDegrees text))
   :longitude-degrees           (read-string (xml1-> trackpoint :Position :LongitudeDegrees text))
   :elevation-meters            (read-string (xml1-> trackpoint :AltitudeMeters text))
   :distance-meters             (read-string (xml1-> trackpoint :DistanceMeters text))
   :heart-rate-bpm              (read-string (xml1-> trackpoint :HeartRateBpm :Value text))})

;(defn fix-distance-trailing-zeroes [trackpoints]
;  (red))


(defn post-process [trackpoints]
  trackpoints)

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

(def trackpoints (parse (io/input-stream-from-file "C:/Users/Anthony/scrap/11368917461.tcx")))

trackpoints

(let [trackpoint-pairs (map vector trackpoints (rest trackpoints))]
  (apply +
         (map (fn [trackpoint-pair]
                (let [tp1 (first trackpoint-pair)
                      tp2 (last trackpoint-pair)
                      lat1 (tp1 :latitude-degrees)
                      lon1 (tp1 :longitude-degrees)
                      lat2 (tp2 :latitude-degrees)
                      lon2 (tp2 :longitude-degrees)]
                  (geo/haversine lat1 lon1 lat2 lon2)))
              trackpoint-pairs)))