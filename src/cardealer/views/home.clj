(ns cardealer.views.home
  (:use compojure.core))

(defroutes main-routes
  (GET "/" []
    (slurp "resources/public/index.html")))

