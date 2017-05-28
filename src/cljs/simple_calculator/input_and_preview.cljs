(ns simple-calculator.input-and-preview
  (:require [simple-calculator.bootstrap-components :as c]
            [reagent.core :as r]
            [simple-calculator.util :as u]))

(defn input [state inputs]
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
                             (when (and (= 13 (.-keyCode %))
                                        (not= @state ""))
                               (u/submit! state inputs)))}])
         :component-did-update
         #(u/update-preview! @state)})]]))

(defn render-area [id & {:keys [hidden?]}]
  [(r/create-class
     {:reagent-render
      (fn []
        [:div
         {:id    id
          :class (when hidden? "hidden")}
         "``"])
      :component-did-mount
      #(js/MathJax.Hub.Queue (array "Typeset" js/MathJax.Hub id))})])

(defn input-and-preview [inputs state]
  (do
    (u/set-up-mathjax!)
    (fn []
      [:div
       [render-area u/render-area-id :hidden? true]
       [c/Row
        [c/Col {:xs 6} [input state inputs]]
        [c/Col {:xs 6} [render-area u/preview-id]]]])))
