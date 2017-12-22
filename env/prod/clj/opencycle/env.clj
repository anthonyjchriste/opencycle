(ns opencycle.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[opencycle started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[opencycle has shut down successfully]=-"))
   :middleware identity})
