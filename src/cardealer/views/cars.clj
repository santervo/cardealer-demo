(ns cardealer.views.cars
  (:use compojure.core)
  (:use somnium.congomongo)
  (:use ring.util.response)
  (:use clojure.data.json))

(defn json-response [data]
  (-> (response (json-str data)) (content-type "application/json")))

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
