(ns simple-calculator.main
  (:require [reagent.core :as r :refer [atom]]
            [simple-calculator.bootstrap-components :as c]
            [ajax.core :as ajax]))

(def preview-id "preview")
(def render-area-id "render-area")

(defn- update-preview! [string]
  (let [jax (get (js/MathJax.Hub.getAllJax preview-id) 0)]
    (js/MathJax.Hub.Queue (array "Text" jax string))))

(defn calculate [string callback]
  (ajax/POST "/api/calculate"
             {:params  {:string string}
              :handler callback}))

(defn render-math [string callback]
  ; callback cannot be #(), must be (fn [])
  (let [jax (get (js/MathJax.Hub.getAllJax render-area-id) 0)]
    (js/MathJax.Hub.Queue (array "Text" jax string))
    (js/MathJax.Hub.Queue (fn [] (callback
                                   (.-innerHTML
                                     (js/document.getElementById
                                       render-area-id)))))))

(defn add-input [inputs input input-rendered result result-rendered]
  (swap! inputs conj {:input           input
                      :input-rendered  input-rendered
                      :result          result
                      :result-rendered result-rendered}))

(defn submit! [state inputs]
  (let [string @state]
    (render-math
      string
      (fn [input-rendered]
        (calculate
          string
          (fn [result]
            (render-math
              result
              (fn [result-rendered]
                (add-input inputs
                           string input-rendered
                           result result-rendered)))))))
    (reset! state "")))

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
                              (submit! state inputs)))}])
         :component-did-update
         #(update-preview! @state)})]]))

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

(defn set-up-mathjax! []
  (js/MathJax.Hub.Config (js-obj "messageStyle" "none"))
  (set! js/MathJax.Hub.processSectionDelay 0))

(defn input-and-preview [inputs state]
  (do
    (set-up-mathjax!)
    (fn []
      [c/Row
       [c/Col {:xs 6} [input state inputs]]
       [c/Col {:xs 6} [render-area preview-id]]])))

(defn past-inputs [inputs state]
  [:div
   (for [{:keys [input input-rendered result result-rendered]} @inputs]
     ^{:key (rand-int js/Number.MAX_VALUE)}
     [c/Row
      {:class "text-center"}
      [:hr]
      (for [[s s-rendered] [[input input-rendered] [result result-rendered]]]
        (let [id (str (rand-int js/Number.MAX_VALUE))]
          ^{:key id}
          [c/Col
           {:xs 6
            :id id}
           [:div
            {:dangerouslySetInnerHTML {:__html s-rendered}
             :style                   {:overflow-x "auto"
                                       :overflow-y "hidden"}}]
           [:br]
           [c/Button {:on-click #(reset! state s)} "Copy"]]))])])

; TODO: Add proper styling
(defn main []
  (let [inputs (atom ())
        state (atom "")]
    [c/Grid
     [render-area render-area-id :hidden? true]
     [input-and-preview inputs state]
     [past-inputs inputs state]]))
