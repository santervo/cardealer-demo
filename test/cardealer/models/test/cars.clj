(ns cardealer.models.test.cars
  (:use cardealer.models.cars :reload)
  (:use clojure.test))

(deftest test-validate-car
  (testing "valid cars"
    (is (empty? (validate-car {:model "Honda" :licenceNumber "XYZ-123" :price "100"})))
    (is (empty? (validate-car {:model "honda" :licenceNumber "xyz-1" :price "100.129021"}))))
  (testing "missing attributes"
    (is (= {:model "Model is required"
            :licenceNumber "Licence number is required"
            :price "Price is required"}
           (validate-car {:model "" :licenceNumber "" :price ""})))))
