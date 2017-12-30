(ns opencycle.mongodb
  (:require
    [monger.collection :as collection]
    [monger.core :as monger]
    [mount.core :as mount]))

(def ^:private DATABASE_NAME "opencycle")
(def ^:private COLLECTION_NAME "rides")

(defn- ensure-indexes [database indexes]
  (doseq [idx indexes]
    (collection/ensure-index database COLLECTION_NAME idx)))

(mount/defstate database-connection
  :start (monger/connect)
  :stop (monger/disconnect database-connection))

(mount/defstate database
  :start (let [db (monger/get-db database-connection DATABASE_NAME)]
           ;(ensure-indexes db [:route-title])
           db))

;(defn- init-indexes! []
;  (do
;    (collection/ensure-index database COLLECTION_NAME :route-title)
;    (collection/ensure-index database COLLECTION_NAME (array-map :start-timestamp 1))
;    (collection/ensure-index database COLLECTION_NAME (array-map :total-time 1))
;    (collection/ensure-index database COLLECTION_NAME (array-map :total-distance 1))))
;
;(defn- init-database! []
;  (do
;    (init-indexes!)))
;
;(init-database!)

(defn save-ride [ride]
  (collection/insert-and-return database COLLECTION_NAME ride))

