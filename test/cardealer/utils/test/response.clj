(ns cardealer.utils.test.response
  (:use cardealer.utils.response :reload)
  (:use clojure.data.json)
  (:use clojure.test))

(deftest test-json-response
  (let [body [{:a "1"}]
        response (json-response body)]
    (is (= 200 (:status response)))
    (is (= "application/json" (get-in response [:headers "Content-Type"])))
    (is (= (json-str body) (:body response)))))
