(ns simple-calculator.macros
  (:require [reagent.core :as r]))

(defmacro adapt-components [& components]
  "Adapt react-bootstrap components into Reagent ones"
  `(do ~@(map (fn [c] `(def ~c (r/adapt-react-class
                                 (aget js/ReactBootstrap ~(str c)))))
              components)))