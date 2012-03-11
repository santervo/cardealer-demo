(defproject cardealer "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [compojure "1.0.1"]
                           [ring-json-params "0.1.3"]
                           [congomongo "0.1.8"]
                           [org.clojure/data.json "0.1.3"]]
            :dev-dependencies [[ring-mock "0.1.1"]]
            :main cardealer.server)

