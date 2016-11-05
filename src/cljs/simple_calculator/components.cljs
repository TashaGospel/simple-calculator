(ns simple-calculator.components
  (:require [reagent.core :as r :refer [atom]]
            [simple-calculator.components.react-bootstrap :as c]))

(def preview-id "preview")

(defn- update-preview! [jax state]
  (when (nil? @jax) (reset! jax (get (js/MathJax.Hub.getAllJax preview-id) 0)))
  (js/MathJax.Hub.Queue (array "Text" @jax @state)))

(defn calculate [state]
  "Mock result")

(defn submit! [state inputs]
  (let [html (.-innerHTML (js/document.getElementById preview-id))]
    (swap! inputs conj {:input @state
                        :preview html
                        :result (calculate @state)})
    (reset! state nil)))

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
  [:div {:id preview-id} "``"])

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
  [:div
   (for [{:keys [input preview result]} @inputs]
     ^{:key (rand-int js/Number.MAX_VALUE)}
     [c/Row
      [c/Col {:xs 4} input]
      [c/Col {:xs 4} [:div {:dangerouslySetInnerHTML {:__html preview}}]]
      [c/Col {:xs 4} result]])])

; inputs: '({:input s/Str :preview s/Str :result s/Str})
(defn main []
  (let [inputs (atom ())]
    [c/Grid
     [input-and-preview inputs]
     [past-inputs inputs]]))