(ns cardealer.views.cars
  (:use cardealer.models.cars)
  (:use compojure.core)
  (:use somnium.congomongo)
  (:use clojure.data.json)
  (:use cardealer.utils.response))

(defn id-to-str [data]
  (update-in data [:_id] str))

(defn money [amount]
  (str (.setScale (bigdec amount) 2)))

(defroutes car-routes
  (GET "/cars" []
    (json-response
      (map id-to-str (fetch :cars))))

  (POST "/cars" {json :body}
    (let [car (-> json slurp read-json)]
      (json-response
        (post-car car)))))

