(defproject cdlib "0.1.0-SNAPSHOT"
  :description "Sample clojure web application"
  :url "https://github.com/grilix/cdlib-sample"
  :min-lein-version "2.0.0"
  :local-repo ".m2"
  :dependencies [[org.clojars.grilix/ring-anti-forgery "1.0.2"]
                 [org.clojars.grilix/formant "0.1.0"]
                 [nav "0.2.0"]
                 [crypto-password "0.1.3"]
                 [selmer "0.7.4"]
                 [org.postgresql/postgresql "9.3-1102-jdbc41"]
                 [korma "0.4.0"]
                 [org.clojure/clojure "1.6.0"]
                 [ring-server "0.3.1"]
                 [ragtime "0.3.7"]]
  :repl-options {:init-ns cdlib.repl}
  :plugins [[lein-ring "0.8.13"] [ragtime/ragtime.lein "0.3.7"]]
  :ring {:handler cdlib.web.handler/app})
