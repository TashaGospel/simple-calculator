(ns simple-calculator.components
  (:require [reagent.core :refer [atom]]
            [simple-calculator.components.react-bootstrap :as c]))

(defn input [state n]
  [c/FormGroup
   {:control-id (str @n)}
   [c/FormControl
    {:type      "text"
     :value     @state
     :on-change #(reset! state (-> % .-target .-value))}]])

(defn preview [state]
  [:div])

(defn past-inputs [inputs]
  (for [input inputs]
    [c/Row]))

; inputs: '({:input s/Str :preview s/Str :result s/Str})
(defn main []
  (let [state (atom "")
        inputs (atom ())
        n (atom 0)]
    [c/Grid
     [c/Row
      [c/Col {:xs 6} [input state n]]
      [c/Col {:xs 6} "Preview"]]
     [past-inputs inputs]]))