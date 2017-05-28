(ns simple-calculator.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [cljsjs.material-ui]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [simple-calculator.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [simple-calculator.main :refer [main]]
            [simple-calculator.navbar :refer [navbar]])
  (:import goog.History))

(defn home-page []
  [main])

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     "this is the story of simple-calculator... work in progress"]]])

(def pages
  {:home  #'home-page
   :about #'about-page})

(defn page []
  [(pages (session/get :page))])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :page :home))

(secretary/defroute "/about" []
  (session/put! :page :about))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn mount-components []
  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (load-interceptors!)
  (hook-browser-navigation!)
  (mount-components))