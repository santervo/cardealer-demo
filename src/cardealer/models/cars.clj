(ns cardealer.models.cars
  (:use somnium.congomongo))

(defn save-car [car]
  (insert! :cars car))
