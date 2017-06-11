(ns simple-calculator.submission
  (:require [cljs.core.async :as a]
            [ajax.core :as ajax]
            [simple-calculator.util :as u])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

; submit -> calculate -> render string -> render result  --> display


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
                    :handler #(go (a/>! out (merge input {:result %})))}))
      ; Callbacks are not called inside current go block,
      ; so must wrap in another go block
      (recur))
    out))

(defn render-math [in in-key out-key]
  (let [out (a/chan)]
    (go-loop []
      (let [input (a/<! in)]
        (let [jax (get (js/MathJax.Hub.getAllJax u/render-area-id) 0)]
          (js/MathJax.Hub.Queue (array "Text" jax (get input in-key)))
          (js/MathJax.Hub.Queue (fn []
                                  (go (a/>!
                                        out
                                        (merge input
                                               {out-key (.-innerHTML
                                                          (js/document.getElementById
                                                            u/render-area-id))})))))))
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

