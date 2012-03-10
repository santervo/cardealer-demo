(ns cardealer.models.test.cars
  (:use cardealer.models.cars :reload)
  (:use clojure.test)
  (:use somnium.congomongo))

(def conn (make-connection "cardealer-test" :host "127.0.0.1" ))

(set-connection! conn)

(deftest test-save-car
  (let [car {:model "Honda" :licenceNumber "XYZ-123" :price "10000.0"}
        car (save-car car)]
    (is (:_id car))
    (is (fetch-one :cars :where {:_id (:_id car)}))

    (testing "updating"
      (let [car (assoc car :model "Toyota")
            car (save-car car)]
        (is (= car (fetch-one :cars :where {:_id (:_id car)})))))))

(deftest test-find-cars
  (do
    (destroy! :cars {})
    (mass-insert! :cars [{:model "Honda"} {:model "Toyota"}])
    (is (= 2 (count (find-cars))))))
