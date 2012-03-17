(ns cardealer.views.cars
  (:use cardealer.models.cars)
  (:use compojure.core)
  (:use somnium.congomongo)
  (:require [clojure.data.json :as json])
  (:use [ring.util.response :only [status]])
  (:use [cardealer.utils.response :only [json-response]]))

(defn id-to-str [data]
  (update-in data [:_id] str))

(defn money [amount]
  (str (.setScale (bigdec amount) 2)))

(defroutes car-routes
  (GET "/cars" []
    (json-response
      (map id-to-str (fetch :cars))))

  (POST "/cars" {json-data :body}
    (let [car (-> json-data slurp json/read-json)
          errors (validate-car car)]
      (if (empty? errors)
        (-> car post-car id-to-str json/json-str)
        (-> (json-response errors) (status 400))))))

