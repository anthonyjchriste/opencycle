(ns ^:figwheel-no-load opencycle.app
  (:require [opencycle.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
