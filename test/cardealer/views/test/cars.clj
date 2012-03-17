(ns cardealer.views.test.cars
  (:require [cardealer.views.cars :as view] :reload-all)
  (:require [cardealer.models.cars :as model] :reload)
  (:require [clojure.data.json :as json])
  (:use ring.mock.request)
  (:import org.bson.types.ObjectId)
  (:use clojure.test))

(def test-car {:model "Citroen" :licenceNumber "CDE-200" :price "15000"})

(defn post-car-request [] 
  (-> (request :post "/cars") 
    (body (json/json-str test-car)) 
    (content-type "application/json")))

(def posted-car-objid (ObjectId.))

(defn mock-post-car [car] (assoc car :_id posted-car-objid))

(defn validate-car-successfully [car] (when (= car test-car) {}))

(def validation-errors {:model "Model name is required" :price "Price is required"})

(defn fail-validation [car] (when (= car test-car) validation-errors))

(defn fail-any [& any] (throw (Exception. "should not have been called")))

(deftest test-post-car
  (testing "given that car is valid"
    (with-redefs [model/post-car mock-post-car
                  model/validate-car validate-car-successfully]
      (let [result (view/car-routes (post-car-request))]
        (is (= 200 (:status result)) "returns status success")
        (is (= (assoc test-car :_id (str posted-car-objid)) (json/read-json (:body result))) "returns post-car result as json"))))
  (testing "given that car has validation errors"
    (with-redefs [model/post-car fail-any
                  model/validate-car fail-validation]
      (let [result (view/car-routes (post-car-request))]
        (is (= 400 (:status result)) "returns status error")
        (is (= validation-errors (json/read-json (:body result))) "returns validation errors as json")))))

 
