(ns simple-calculator.util
  (:require [ajax.core :as ajax]))

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

(defn set-up-mathjax! []
  (js/MathJax.Hub.Config (js-obj "messageStyle" "none"))
  (set! js/MathJax.Hub.processSectionDelay 0))

