(ns cardealer.utils.validation)

(defn- field-is [error-map obj field is error-msg]
  (if (is (get obj field))
    error-map
    (assoc error-map field error-msg)))

(defn required [error-map obj field error-msg]
  (field-is error-map obj field #(not (empty? %)) error-msg))


