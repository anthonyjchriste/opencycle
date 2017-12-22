(ns user
  (:require 
            [mount.core :as mount]
            [opencycle.figwheel :refer [start-fw stop-fw cljs]]
            opencycle.core))

(defn start []
  (mount/start-without #'opencycle.core/repl-server))

(defn stop []
  (mount/stop-except #'opencycle.core/repl-server))

(defn restart []
  (stop)
  (start))


