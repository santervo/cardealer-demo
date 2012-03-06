(ns cardealer.views.home
  (:use [noir.core :only [defpage]]))

(defpage "/" []
  (slurp "resources/public/index.html"))

