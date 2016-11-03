(ns simple-calculator.components
  (:require [reagent.core :as r :refer [atom]]
            [simple-calculator.components.react-bootstrap :as c]))

(defn- update-preview! [jax state]
  (when (nil? @jax) (reset! jax (get (js/MathJax.Hub.getAllJax "preview") 0)))
  (js/MathJax.Hub.Queue (to-array ["Text" @jax @state])))

(defn submit! [state inputs])

(defn input [state inputs]
  (let [jax (atom nil)]
    (fn []
      [c/FormGroup
       {:control-id "input"}
       [:input.form-control.input-lg
        {:type        :text
         :value       @state
         :on-change   #(do
                        (reset! state (-> % .-target .-value))
                        (update-preview! jax state))
         :on-key-down #(if (= 13 (.-keyCode %)) (submit! state inputs))}]])))

(defn preview []
  [:div {:id "preview"} "``"])

(defn set-up-mathjax! []
  (js/MathJax.Hub.Config (js-obj "messageStyle" "none"))
  (set! js/MathJax.Hub.processSectionDelay 0))

(defn input-and-preview [inputs]
  (do
    (set-up-mathjax!)
    (let [state (atom "")]
      (fn []
        [c/Row
         [c/Col {:xs 6} [input state inputs]]
         [c/Col {:xs 6} [preview]]]))))

(defn past-inputs [inputs]
  (for [input inputs]
    [c/Row
     [c/Col {:xs 4}]]))

; inputs: '({:input s/Str :preview s/Str :result s/Str})
(defn main []
  (let [inputs (atom ())]
    [c/Grid
     [input-and-preview inputs]]))
;[past-inputs inputs]]))