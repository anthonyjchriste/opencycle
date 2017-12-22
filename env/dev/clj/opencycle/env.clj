(ns opencycle.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [opencycle.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[opencycle started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[opencycle has shut down successfully]=-"))
   :middleware wrap-dev})
