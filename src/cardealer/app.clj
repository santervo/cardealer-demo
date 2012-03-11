(ns cardealer.app
  (:use cardealer.views.home cardealer.views.cars)
  (:use compojure.core compojure.handler)
  (:use ring.middleware.json-params))

(defroutes app-routes 
  main-routes
  car-routes)

(def app
  (wrap-json-params 
    (api app-routes)))
