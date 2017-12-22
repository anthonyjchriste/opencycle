(ns opencycle.io.io
  (:import (java.io ByteArrayInputStream)))

(defn input-stream-from-string [str]
  (ByteArrayInputStream. (.getBytes str "UTF-8")))

(defn input-stream-from-file [file]
  (clojure.java.io/input-stream file))