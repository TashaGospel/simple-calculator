(ns simple-calculator.input-wrapper
  (:require [simple-calculator.submission :as sub]
            [simple-calculator.input-system :as in]))

(defn input-wrapper [inputs & children]
  [:div
   {;:on-change   #(do
    ;                (reset! state (-> % .-target .-value)))
    :on-key-down  #(do
                     (let [string (in/get-string)]
                       (when (and (= 13 (.-keyCode %))
                                  (not= string ""))
                         ;(reset! state "")
                         (sub/submit! string inputs))))
    :on-key-press #(in/input-character! (.-key %))}
   children])

