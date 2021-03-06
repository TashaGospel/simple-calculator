(ns simple-calculator.preview
  (:require [simple-calculator.bootstrap-components :as c]
            [reagent.core :as r]
            [cljs-react-material-ui.reagent :as ui]
            [simple-calculator.util :as u]
            [simple-calculator.submission :as sub]
            [simple-calculator.input-system :as i]))

;(defn input [state inputs]
;  (fn []
;    [c/FormGroup
;     {:control-id "input"}
;     [(r/create-class
;        {:reagent-render
;         (fn []
;           [ui/text-field
;            {:hint-text    "Type an equation"
;             :value        @state
;             ;:on-change   #(do
;             ;                (reset! state (-> % .-target .-value)))
;             :on-key-down  #(do
;                              (let [string @state]
;                                (when (and (= 13 (.-keyCode %))
;                                           (not= string ""))
;                                  (reset! state "")
;                                  (sub/submit! string inputs))))
;             :on-key-press #(i/input-character state (.-key %))}])
;         :component-did-update
;         #(u/update-preview! @state)})]]))

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

(defn preview [inputs state]
  (do
    (u/set-up-mathjax!)
    (fn []
      [:div
       [render-area u/render-area-id :hidden? true]
       [c/Row
        ;[c/Col {:xs 6} [input state inputs]]
        [c/Col {:xs 12} [render-area u/preview-id]]]])))
