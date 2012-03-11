(ns cardealer.views.test.cars
  (:use cardealer.app :reload-all)
  (:use cardealer.models.cars)
  (:use clojure.data.json)
  (:use ring.mock.request)
  (:use somnium.congomongo)
  (:use clojure.test))

(def initial-cars [{:model "Honda"} {:model "Toyota"}])

(def test-conn (make-connection "cardealer-test" :host "127.0.0.1"))

(defn insert-test-data []
  (with-mongo test-conn
    (destroy! :cars {})
    (mass-insert! :cars initial-cars)))

(defn test-app [request]
  (insert-test-data)
  (with-redefs [conn test-conn]
    (#'app request)))

(deftest test-get-cars
  (let [result (#'test-app (request :get "/cars"))
        cars (read-json (:body result))]
    (is (= ["Honda" "Toyota"] (map :model cars)) "Returns correct cars")))

(deftest test-post-car
  (let [request (-> (request :post "/cars") 
                  (body (json-str {:model "Citroen"}))
                  (content-type "application/json"))
          result (#'test-app request)
          car (read-json (:body result))]
    (is (:_id car) "Returns id for car")))
