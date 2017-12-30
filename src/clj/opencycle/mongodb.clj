(ns opencycle.mongodb
  (:require
    [monger.collection :as collection]
    [monger.core :as monger]
    [monger.operators :as op]
    [mount.core :as mount]
    [monger.query :as query])
  (:import (org.bson.types ObjectId)))

(def ^:private DATABASE_NAME "opencycle")
(def ^:private COLLECTION_NAME "rides")

(mount/defstate database-connection
  :start (monger/connect)
  :stop (monger/disconnect database-connection))

(mount/defstate database
  :start (monger/get-db database-connection DATABASE_NAME))

(defn- object-id [oid-str]
  (ObjectId. ^String oid-str))

(defn save-ride [ride]
  (collection/insert-and-return database COLLECTION_NAME ride))

(defn ride-descriptions [num-results results-offset]
  (query/with-collection database COLLECTION_NAME
                         (query/find {})
                         (query/sort {:start-timestamp -1})
                         (query/fields {:sample-points 0
                                        :original-file 0
                                        :original-file-size-bytes 0
                                        :geo-notes 0})
                         (query/paginate :page results-offset :per-page num-results)))

(defn ride-details [oid]
  (collection/find-map-by-id database COLLECTION_NAME (object-id oid) {:subsampled-locations 0
                                                                       :original-file 0
                                                                       :original-file-size-bytes 0}))

