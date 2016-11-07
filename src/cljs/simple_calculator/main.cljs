(ns simple-calculator.main
  (:require [reagent.core :as r :refer [atom]]
            [simple-calculator.bootstrap-components :as c]))

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
    (reset! state "")))

(defn input [state inputs]
  (let [jax (atom nil)]
    (fn []
      [c/FormGroup
       {:control-id "input"}
       [(r/create-class
         {:reagent-render
          (fn []
            [:input.form-control
             {:type        :text
              :value       @state
              :on-change   #(do
                             (reset! state (-> % .-target .-value)))
              :on-key-down #(do
                             (when (= 13 (.-keyCode %))
                               (submit! state inputs)))}])
          :component-did-update #(update-preview! jax state)})]])))

(defn preview []
  [(r/create-class
     {:reagent-render
      (fn []
        [:div {:id preview-id} "``"])
      :component-did-mount
      #(js/MathJax.Hub.Queue (array "Typeset" js/MathJax.Hub preview-id))})])

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
      [:hr]
      [c/Col {:class "text-center"} input]
      [c/Col {:class "text-center"} [:div {:dangerouslySetInnerHTML {:__html preview}}]]
      [c/Col {:class "text-center"} result]])])

; inputs: '({:input s/Str :preview s/Str :result s/Str})
; TODO: Add proper styling
(defn main []
  (let [inputs (atom ())]
    [c/Grid
     [input-and-preview inputs]
     [past-inputs inputs]]))