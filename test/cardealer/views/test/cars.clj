(ns cardealer.views.test.cars
  (:use cardealer.app :reload-all)
  (:use cardealer.models.cars)
  (:use clojure.data.json)
  (:use ring.mock.request)
  (:use clojure.test))

(defn mock-find-cars []
  [{:model "Honda"} {:model "Toyota"}])

(defn mock-save-car [car]
  (assoc car :_id 100))

(deftest test-get-cars
  (with-redefs [find-cars mock-find-cars]
    (let [result (#'app (request :get "/cars"))]
        (is (= (find-cars) (read-json (:body result)))))))

(deftest test-post-car
  (with-redefs [save-car mock-save-car]
    (let [request (-> (request :post "/cars") 
                    (body (json-str {:model "Rolls-Royce"}))
                    (content-type "application/json"))
          result (#'app request)]
      (is (= {:model "Rolls-Royce" :_id 100} (read-json (:body result)))))))
