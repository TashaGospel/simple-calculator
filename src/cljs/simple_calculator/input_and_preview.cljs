(ns simple-calculator.input-and-preview
  (:require [simple-calculator.bootstrap-components :as c]
            [reagent.core :as r]
            [cljs-react-material-ui.reagent :as ui]
            [simple-calculator.util :as u]))

(defn input [state inputs]
  (fn []
    [c/FormGroup
     {:control-id "input"}
     [(r/create-class
        {:reagent-render
         (fn []
           [ui/text-field
            {:hint-text   "Type equation here"
             :value       @state
             :on-change   #(do
                             (reset! state (-> % .-target .-value)))
             :on-key-down #(do
                             (let [string @state]
                               (when (and (= 13 (.-keyCode %))
                                          (not= string ""))
                                 (u/submit! string inputs)))
                             (reset! state ""))}])
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
