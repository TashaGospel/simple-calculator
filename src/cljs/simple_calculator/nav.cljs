(ns simple-calculator.nav
  (:require [simple-calculator.bootstrap-components :as c]
            [reagent.session :as session]))

(defn navbar []
  [c/Navbar
   {:inverse true}
    ;:collapse-on-select true} ; Bug with react-bootstrap?
   [c/Navbar-Header
    [c/Navbar-Brand
     [:a {:href "#/"} "Simple Calculator"]]
    [c/Navbar-Toggle]]
   [c/Navbar-Collapse
    [c/Nav
     {:active-key (session/get :page)}
     [c/NavItem
      {:event-key :home :href "#/"}
      "Home"]
     [c/NavItem
      {:event-key :about :href "#/about"}
      "About"]]]])