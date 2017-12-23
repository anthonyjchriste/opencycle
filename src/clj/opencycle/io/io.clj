(ns opencycle.io.io
  (:require [clojure.java.io :as io])
  (:import (java.io ByteArrayInputStream ByteArrayOutputStream)))

(defn input-stream-from-bytes [bytes]
  (ByteArrayInputStream. bytes))

(defn input-stream-from-string [str]
  (input-stream-from-bytes (.getBytes str "UTF-8")))

(defn input-stream-from-file [file]
  (clojure.java.io/input-stream file))

(defn bytes-from-input-stream [input-stream]
  (with-open [out (ByteArrayOutputStream.)]
    (io/copy input-stream out)
    (.toByteArray out)))