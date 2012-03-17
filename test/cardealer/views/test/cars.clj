(ns cardealer.views.test.cars
  (:require [cardealer.views.cars :as view] :reload-all)
  (:require [cardealer.models.cars :as model])
  (:use cardealer.utils.response :reload)
  (:use clojure.data.json)
  (:use ring.mock.request)
  (:use somnium.congomongo)
  (:use clojure.test))

(def test-car {:model "Citroen" :licenceNumber "CDE-200" :price "15000"})

(def post-car-request (-> (request :post "/cars") 
                        (body (json-str test-car)) 
                        (content-type "application/json")))

(defn mock-post-car [car] (assoc car :_id 1))

(defn validate-car-successfully [car] (when (= car test-car) {}))

(deftest test-post-car
  (testing "given that car is valid"
    (with-redefs [model/post-car mock-post-car
                  model/validate-car validate-car-successfully]
      (let [result (view/car-routes post-car-request)]
        (is (= 200 (:status result)) "returns status success")
        (is (= (assoc test-car :_id 1) (read-json (:body result))) "returns post-car result as json")))))


