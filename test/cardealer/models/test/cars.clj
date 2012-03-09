(ns cardealer.models.test.cars
  (:use cardealer.models.cars :reload)
  (:use clojure.test)
  (:use somnium.congomongo))

(def conn (make-connection "cardealer-test" :host "127.0.0.1" ))

(set-connection! conn)

(deftest test-save
  (let [car {:model "Honda" :licenceNumber "XYZ-123" :price "10000.0"}
        car (save-car car)]
    (is (:_id car))
    (is (fetch-one :cars :where {:id (get :_id car)}))))
