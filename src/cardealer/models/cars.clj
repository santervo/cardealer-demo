(ns cardealer.models.cars
  (:use somnium.congomongo))

(defn save-car [car]
  (if (:_id car)
    (do
      (update! :cars {:_id (:_id car)} car)
      car)
    (insert! :cars car)))

(defn find-cars []
  (fetch :cars))
