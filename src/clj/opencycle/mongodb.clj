(ns opencycle.mongodb
  (:require
    [monger.collection :as collection]
    [monger.core :as monger]
    [monger.operators :as op]
    [mount.core :as mount]
    [monger.query :as query]))

(def ^:private DATABASE_NAME "opencycle")
(def ^:private COLLECTION_NAME "rides")

(mount/defstate database-connection
  :start (monger/connect)
  :stop (monger/disconnect database-connection))

(mount/defstate database
  :start (monger/get-db database-connection DATABASE_NAME))

(defn save-ride [ride]
  (collection/insert-and-return database COLLECTION_NAME ride))

(defn ride-descriptions [num-results results-offset]
  (query/with-collection database COLLECTION_NAME
                         (query/find {})
                         (query/sort {:start-timestamp -1})
                         (query/paginate :page results-offset :per-page num-results)))

