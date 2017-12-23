(ns opencycle.lz4
  (:require [opencycle.io.io :as io])
  (:import (net.jpountz.lz4 LZ4Factory)))


(defn get-compressor []
  (let [factory (LZ4Factory/fastestInstance)]
    (.highCompressor factory)))

(defn lz4-compress
  ([bytes]
    (lz4-compress bytes (get-compressor)))
  ([bytes compressor]
    (.compress compressor bytes)))
