(ns simple-calculator.app
  (:require [simple-calculator.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
