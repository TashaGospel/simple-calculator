(ns simple-calculator.components.react-bootstrap
  (:require [cljsjs.react-bootstrap])
  (:require-macros [simple-calculator.macros :as m]))

(declare FormControl FormGroup Grid Row Col) ; this is here until Cursive supports "Resolve as declare"
(m/adapt-components FormControl FormGroup Grid Row Col)
