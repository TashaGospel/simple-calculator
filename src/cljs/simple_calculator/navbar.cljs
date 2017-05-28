(ns simple-calculator.navbar
  (:require [simple-calculator.bootstrap-components :as c]
            [reagent.session :as session]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]))

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

;(defn navbar []
;  [ui/mui-theme-provider
;   {:mui-theme (get-mui-theme)}
;   [ui/app-bar
;    {:title "Simple Calculator"}]])