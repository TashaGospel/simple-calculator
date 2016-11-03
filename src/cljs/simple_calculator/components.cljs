(ns simple-calculator.components
  (:require [reagent.core :as r :refer [atom]]
            [simple-calculator.components.react-bootstrap :as c]))

(defn- update-preview [jax state]
  (when (nil? @jax) (reset! jax (get (js/MathJax.Hub.getAllJax "preview") 0)))
  (js/MathJax.Hub.Queue (to-array ["Text" @jax @state])))

(defn input [state]
  (let [jax (atom nil)]
    (fn []
      [c/FormGroup
       {:control-id "input"}
       [c/FormControl
        {:type      "text"
         :value     @state
         :on-change #(do
                      (reset! state (-> % .-target .-value))
                      (update-preview jax state))}]])))


; Could have used an invisible div to trigger re-render
(defn preview []
  [:div {:id "preview"} "``"])

(defn input-and-preview []
  (let [state (atom "")]
    (fn []
      [c/Row
       [c/Col {:xs 6} [input state]]
       [c/Col {:xs 6} [preview]]])))
;[c/Col {:xs 6} [preview state]]])))

(defn past-inputs [inputs]
  (for [input inputs]
    [c/Row]))

; inputs: '({:input s/Str :preview s/Str :result s/Str})
(defn main []
  (let [inputs (atom ())]
    [c/Grid
     [input-and-preview]]))
;[past-inputs inputs]]))