(ns simple-calculator.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [simple-calculator.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[simple-calculator started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[simple-calculator has shut down successfully]=-"))
   :middleware wrap-dev})
