(ns cardealer.models.cars
  (:use cardealer.utils.validation))

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

