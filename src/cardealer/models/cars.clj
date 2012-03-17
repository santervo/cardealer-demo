(ns cardealer.models.cars
  (:require [somnium.congomongo :as mongo])
  (:use cardealer.utils.validation))

(defn post-car [car]
  (mongo/insert! :cars car))

(defn validate-car [car]
  (for-object car
    (field :model
      (is-required "Model is required"))
    (field :licenceNumber
      (is-required "Licence number is required")
      (matches-pattern #"^[a-zA-ZåäöÅÄÖ]{1,3}-\d{1,3}$" "Licence number is not valid"))
    (field :price
      (is-required "Price is required")
      (matches-pattern #"^(\d+\.?\d*|\d*\.?\d+)$" "Price is not valid"))))

