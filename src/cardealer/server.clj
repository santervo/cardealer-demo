(ns cardealer.server
  (:use ring.adapter.jetty ring.middleware.reload)
  (:use cardealer.app))

(def dev-handler (wrap-reload #'app))

(defn -main [& m]
  (run-jetty #'dev-handler {:port 8080}))

