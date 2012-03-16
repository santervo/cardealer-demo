(ns cardealer.views.test.cars
  (:use cardealer.app :reload-all)
  (:use clojure.data.json)
  (:use ring.mock.request)
  (:use somnium.congomongo)
  (:use clojure.test))

(def initial-cars [{:model "Honda" :licenceNumber "XYZ-123" :price "5000"}
                   {:model "Toyota" :licenceNumber "ABC-100" :price "9000"}])

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
  (testing "should return all cars"
    (let [result (#'test-app (request :get "/cars"))
          cars (read-json (:body result))]
      (is (= ["Honda" "Toyota"] (map :model cars))))))

(deftest test-post-car
  (testing "should insert car to database"
    (let [request (-> (request :post "/cars") 
                    (body (json-str {:model "Citroen" :licenceNumber "CDE-200" :price "15000"}))
                    (content-type "application/json"))
            result (#'test-app request)
            car (read-json (:body result))]
      (with-mongo test-conn
        (is (:_id car) "returns id")
        (is (fetch-by-id :cars (object-id (:_id car))))))))
