(ns simple-calculator.util
  (:require [ajax.core :as ajax]
            [cljs.core.async :as a])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(def preview-id "preview")
(def render-area-id "render-area")

; submit -> calculate -> render string -> render result  --> display

(defn- update-preview! [string]
  (let [jax (get (js/MathJax.Hub.getAllJax preview-id) 0)]
    (js/MathJax.Hub.Queue (array "Text" jax string))))

(defn submit [in]
  (let [out (a/chan)]
    (go-loop []
      (a/>! out {:string (a/<! in)})
      (recur))
    out))

(defn calculate [in]
  (let [out (a/chan)]
    (go-loop []
      (let [input (a/<! in)]
        (ajax/POST "/api/calculate"
                   {:params  {:string (:string input)}
                    :handler #(do
                                ;(println (merge input {:result %}))
                                (a/>! out (merge input {:result %}))
                                (println "sent"))}))
      (recur))
    out))

(defn render-math [in in-key out-key]
  (let [out (a/chan)]
    (go-loop []
      (let [input (a/<! in)]
        (let [jax (get (js/MathJax.Hub.getAllJax render-area-id) 0)]
          (js/MathJax.Hub.Queue (array "Text" jax (get input in-key)))
          (js/MathJax.Hub.Queue (fn [] (a/>!
                                         out
                                         (merge input
                                                {out-key (.-innerHTML
                                                           (js/document.getElementById
                                                             render-area-id))}))))))
      (recur))
    out))

(def input-chan (a/chan))
(def submit-chan (submit input-chan))
(def calculate-chan (calculate submit-chan))
(def string-render-chan (render-math calculate-chan :string :string-rendered))
(def result-render-chan (render-math string-render-chan :result :result-rendered))

(defn submit! [string past-results]
  (go
    (a/>! input-chan string)
    (swap! past-results conj (a/<! result-render-chan))))

(defn set-up-mathjax! []
  (js/MathJax.Hub.Config (js-obj "messageStyle" "none"))
  (set! js/MathJax.Hub.processSectionDelay 0))