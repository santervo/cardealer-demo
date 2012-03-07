(ns cardealer.views.cars
  (:use [noir.core :only [defpage]]
        [noir.response :only [json]]))

(defpage "/cars" []
  (json [{:model "Honda" :licenceNumber "XYZ-123" :price "100.0"}]))
