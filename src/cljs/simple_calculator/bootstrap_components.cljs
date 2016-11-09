(ns simple-calculator.bootstrap-components
  (:require [cljsjs.react-bootstrap]
            [reagent.core])
  (:require-macros [simple-calculator.macros :as m]))

(declare FormControl FormGroup Grid Row Col Navbar Navbar-Header Navbar-Brand Navbar-Toggle Navbar-Collapse NavItem Nav Button) ; this is here until Cursive supports "Resolve as declare"
(m/adapt-components FormControl FormGroup Grid Row Col Navbar (Navbar Header Brand Toggle Collapse) NavItem Nav Button)
