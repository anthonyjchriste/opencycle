(ns opencycle.routes.api
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.coercions :refer :all]

            [opencycle.mongodb :as mongodb]))

(defroutes api-routes
           (GET "/api/ride-descriptions" [page :<< as-int]
             {:body (if page
                      (mongodb/ride-descriptions 15 page)
                      (mongodb/ride-descriptions 15 1))}))