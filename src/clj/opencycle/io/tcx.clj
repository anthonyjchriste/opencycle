(ns opencycle.io.tcx
  (:require [clojure.data.xml :as xml]
            [clojure.zip :as zip]
            [clojure.data.zip.xml :refer [text xml-> xml1->]]
            [opencycle.geo :as geo]
            [opencycle.io.io :as io]
            [opencycle.lz4 :as lz4]
            [opencycle.models :as models]
            [opencycle.stats :as stats])
  (:import (java.time ZonedDateTime)
           (java.io ByteArrayInputStream)))

(defn parse-date-time [datetime]
  (-> datetime
      ZonedDateTime/parse
      .toInstant
      .getEpochSecond))

(defn parse-trackpoint [trackpoint]
  {:timestamp      (parse-date-time (xml1-> trackpoint :Time text))
   :latitude       (read-string     (xml1-> trackpoint :Position :LatitudeDegrees text))
   :longitude      (read-string     (xml1-> trackpoint :Position :LongitudeDegrees text))
   :elevation      (read-string     (xml1-> trackpoint :AltitudeMeters text))
   :distance       (read-string     (xml1-> trackpoint :DistanceMeters text))
   :heart-rate-bpm (read-string     (xml1-> trackpoint :HeartRateBpm :Value text))})

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
  (let [bytes (io/bytes-from-input-stream input-stream)
        compressed (lz4/lz4-compress bytes)
        parsed-xml (xml/parse (io/input-stream-from-bytes bytes))
        zipped-xml (zip/xml-zip parsed-xml)
        zipped-trackpoints (xml-> zipped-xml
                                  :TrainingCenterDatabase
                                  :Activities
                                  :Activity
                                  :Lap
                                  :Track
                                  :Trackpoint)
        trackpoints (post-process (map parse-trackpoint zipped-trackpoints))
        sample-points (models/make-samples #(models/make-sample (:timestamp %)
                                                                (:latitude %)
                                                                (:longitude %)
                                                                (:elevation %)
                                                                (:distance %)
                                                                {:heart-rate-bpm (:heart-rate-bpm %)})
                                           trackpoints)]
    (models/make-ride sample-points compressed (count bytes))))





(def ride (parse (io/input-stream-from-file "C:/Users/Anthony/Downloads/11368917461.tcx")))
ride




