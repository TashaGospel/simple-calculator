(ns simple-calculator.input-wrapper
  (:require [simple-calculator.submission :as sub]
            [simple-calculator.input-system :as in]
            [simple-calculator.util :as u]))

(defn input-wrapper [inputs child]
  [:div
   {;:on-change   #(do
    ;                (reset! state (-> % .-target .-value)))
    :tab-index    "1"
    :on-key-down  #(do
                     (let [string (in/get-string)]
                       (when (and (= 13 (.-keyCode %))
                                  (not= string ""))
                         ;(reset! state "")
                         (u/update-preview! "")
                         (sub/submit! string inputs))))
    :on-key-press #(do
                     (println (.-key %))
                     (u/update-preview! (in/input-character! (.-key %))))
    :style        {:flex 1}}
   ^{:key (random-uuid)} child])

