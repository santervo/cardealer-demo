(ns cardealer.views.home
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [include-css include-js html5]]))

(defn- head []
  [:head
   [:title "CarDealer"]
   (include-css "/css/reset.css")])

(defn- body []
  [:body
    [:div
     ]])
 
(defpage "/" []
  (html5
    (head)
    (body)))

