(ns cardealer.views.cars
  (:use compojure.core)
  (:use somnium.congomongo)
  (:use cardealer.utils.response))

(defn id-to-str [data]
  (update-in data [:_id] str))

(defn money [amount]
  (str (.setScale (bigdec amount) 2)))

(defroutes car-routes
  (GET "/cars" []
    (json-response
      (map id-to-str (fetch :cars))))

  (POST "/cars" {car :json-params}
    (let [car (update-in car ["price"] money)]
      (json-response
        (-> (insert! :cars car)
          (id-to-str)
          (select-keys [:_id]))))))
