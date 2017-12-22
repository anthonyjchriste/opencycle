(ns opencycle.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [opencycle.core-test]))

(doo-tests 'opencycle.core-test)

