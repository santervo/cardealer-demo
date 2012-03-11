(ns cardealer.views.cars
  (:use cardealer.models.cars)
  (:use compojure.core)
  (:use clojure.data.json))

(defroutes car-routes
  (GET "/cars" [] (json-str (find-cars)))

  (POST "/cars" {car :json-params}
        (json-str (save-car car))))
