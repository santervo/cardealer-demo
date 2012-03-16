(ns cardealer.models.cars
  (:use cardealer.utils.validation))

(defn validate-car [car]
  (-> {}
    (required car :model "Model is required")
    (required car :licenceNumber "Licence number is required")
    (required car :price "Price is required")))

