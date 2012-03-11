(ns cardealer.app
  (:use cardealer.views.home cardealer.views.cars)
  (:use compojure.core compojure.handler compojure.route)
  (:use somnium.congomongo)
  (:use ring.middleware.json-params))

(def conn (make-connection "cardealer" :host "127.0.0.1"))

(defroutes app-routes
  (resources "/" :root "resources/public")
  main-routes
  car-routes)

(defn with-mongodb [handler]
  (fn [req]
    (with-mongo conn
      (handler req))))

(def app
  (with-mongodb
    (wrap-json-params
      (api app-routes))))
