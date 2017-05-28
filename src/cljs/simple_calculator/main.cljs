(ns simple-calculator.main
  (:require [reagent.core :as r :refer [atom]]
            [simple-calculator.bootstrap-components :as c]
            [ajax.core :as ajax]
            [simple-calculator.util :as u]
            [simple-calculator.input-and-preview :refer [input-and-preview]]
            [simple-calculator.past-inputs :refer [past-inputs]]))

; TODO: Add proper styling
(defn main []
  (let [inputs (atom ())
        state (atom "")]
    [c/Grid
     [input-and-preview inputs state]
     [past-inputs inputs state]]))
