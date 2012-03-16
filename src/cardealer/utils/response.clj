(ns cardealer.utils.response
  (:use ring.util.response)
  (:use clojure.data.json))

(defn json-response [data]
  (-> (response (json-str data)) (content-type "application/json")))

(defn error-response [response]
  (status response 400))
