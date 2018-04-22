(ns opencycle.routes.home
  (:require [opencycle.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "home.html"))

(defn base-page []
  (layout/render "base.html"))

(defroutes home-routes
  (GET "/" []
       (base-page))
  (GET "/upload/tcx" [])
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8"))))

