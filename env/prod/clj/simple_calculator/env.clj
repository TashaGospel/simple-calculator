(ns simple-calculator.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[simple-calculator started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[simple-calculator has shut down successfully]=-"))
   :middleware identity})
