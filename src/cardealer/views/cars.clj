(ns cardealer.views.cars
  (:use [noir.core :only [defpage]]
        [noir.response :only [json]]))

(defpage "/cars" []
  (json [{:licenceNumber "XYZ-123"}]))
