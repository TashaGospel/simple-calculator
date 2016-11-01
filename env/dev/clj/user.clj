(ns user
  (:require [mount.core :as mount]
            [simple-calculator.figwheel :refer [start-fw stop-fw cljs]]
            simple-calculator.core))

(defn start []
  (mount/start-without #'simple-calculator.core/repl-server))

(defn stop []
  (mount/stop-except #'simple-calculator.core/repl-server))

(defn restart []
  (stop)
  (start))


