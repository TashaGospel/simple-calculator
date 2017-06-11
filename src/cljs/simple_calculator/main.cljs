(ns simple-calculator.main
  (:require [reagent.core :as r :refer [atom]]
            [simple-calculator.bootstrap-components :as c]
            [ajax.core :as ajax]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [simple-calculator.util :as u]
            [simple-calculator.preview :refer [preview]]
            [simple-calculator.past-inputs :refer [past-inputs-list]]
            [simple-calculator.input-wrapper :refer [input-wrapper]]))

; TODO: Add proper styling
(defn main []
  (let [inputs (atom ())
        state (atom "")]
    [ui/mui-theme-provider
     {:mui-theme (get-mui-theme)}
     [input-wrapper
      inputs
      [c/Grid
       [preview inputs state]
       [past-inputs-list inputs state]]]]))


