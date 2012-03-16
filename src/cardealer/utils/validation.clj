(ns cardealer.utils.validation)

(defn- field-is [[error-map obj] field is error-msg]
  (if (is (get obj field))
    [error-map obj]
    [(assoc error-map field error-msg) obj]))

(defn required [result field error-msg]
  (field-is result field #(not (empty? %)) error-msg))

(defn pattern [result field pattern error-msg]
  (field-is result field #(re-find pattern %) error-msg))

(defmacro validate [obj & exprs]
  `(first (-> [{} ~obj] ~@exprs)))

(defn as-second [x coll]
  (concat [(first coll) x] (rest coll)))

(defmacro validate-field [result field & exprs]
  `(-> ~result ~@(map #(as-second field %) (reverse exprs))))
