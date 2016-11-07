(ns simple-calculator.macros
  (:require [reagent.core :as r]
            [clojure.string :as string]))

(defmacro adapt-list [[base & names]]
  `(do ~@(map (fn [name]
                `(def ~(symbol (str base "-" name))
                   (r/adapt-react-class
                     (aget (aget js/ReactBootstrap ~(str base)) ~(str name)))))
              names)))

(defmacro adapt-components [& components]
  "Adapt react-bootstrap components into Reagent ones"
  `(do ~@(map (fn [c]
                (if (list? c)
                  `(adapt-list ~c)
                  `(def ~c
                     (r/adapt-react-class
                       (aget js/ReactBootstrap ~(str c))))))
              components)))