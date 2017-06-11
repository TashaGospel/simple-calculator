(ns simple-calculator.input-system
  (:require [simple-calculator.system-interface :as in]))

(def input-system (new js/InputSystem))

(defn input-character! [character]
  (in/input-character input-system character)
  (str input-system))

(defn get-string []
  (str input-system))


