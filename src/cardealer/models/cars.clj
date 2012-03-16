(ns cardealer.models.cars
  (:use cardealer.utils.validation))

(defn validate-car [car]
  (validate car
    (required :model "Model is required")
    (validate-field :licenceNumber
      (required "Licence number is required")
      (pattern #"^[a-zA-ZåäöÅÄÖ]{1,3}-\d{1,3}$" "Licence number is not valid"))
    (required :price "Price is required")))

