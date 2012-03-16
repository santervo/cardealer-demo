(ns cardealer.models.test.cars
  (:use cardealer.models.cars :reload-all)
  (:use clojure.test))

(deftest test-validate-car
  (testing "valid cars"
    (is (empty? (validate-car {:model "Honda" :licenceNumber "ÅÄÖ-123" :price "1"})))
    (is (empty? (validate-car {:model "Honda" :licenceNumber "åäö-123" :price "100"})))
    (is (empty? (validate-car {:model "Honda" :licenceNumber "AOZ-123" :price "100."})))
    (is (empty? (validate-car {:model "Honda" :licenceNumber "aoz-123" :price ".100"})))
    (is (empty? (validate-car {:model "Honda" :licenceNumber "A-1" :price "100.129021"}))))
  (testing "missing attributes"
    (is (= {:model "Model is required"
            :licenceNumber "Licence number is required"
            :price "Price is required"}
           (validate-car {:model "" :licenceNumber "" :price ""}))))
  (testing "invalid licence number"
    (is (= {:licenceNumber "Licence number is not valid"}
           (validate-car {:model "Tata" :licenceNumber "xyzx-123" :price "100"})))
    (is (= {:licenceNumber "Licence number is not valid"}
           (validate-car {:model "Tata" :licenceNumber "xyz-1234" :price "100"})))
    (is (= {:licenceNumber "Licence number is not valid"}
           (validate-car {:model "Tata" :licenceNumber "-123" :price "100"})))
    (is (= {:licenceNumber "Licence number is not valid"}
           (validate-car {:model "Tata" :licenceNumber "xyz-" :price "100"})))
    (is (= {:licenceNumber "Licence number is not valid"}
           (validate-car {:model "Tata" :licenceNumber "xyz123" :price "100"})))
    (is (= {:licenceNumber "Licence number is not valid"}
           (validate-car {:model "Tata" :licenceNumber "123-zyx" :price "100"}))))
  (testing "invalid price"
     (is (= {:price "Price is not valid"}
           (validate-car {:model "Tata" :licenceNumber "XYZ-123" :price "."})))
     (is (= {:price "Price is not valid"}
           (validate-car {:model "Tata" :licenceNumber "XYZ-123" :price "100K"})))))
