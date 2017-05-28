(ns simple-calculator.past-inputs
  (:require [simple-calculator.bootstrap-components :as c]))

(defn past-inputs [inputs state]
  [:div
   (for [{:keys [input input-rendered result result-rendered]} @inputs]
     ^{:key (rand-int js/Number.MAX_VALUE)}
     [c/Row
      {:class "text-center"}
      [:hr]
      (for [[s s-rendered] [[input input-rendered] [result result-rendered]]]
        (let [id (str (rand-int js/Number.MAX_VALUE))]
          ^{:key id}
          [c/Col
           {:xs 6
            :id id}
           [:div
            {:dangerouslySetInnerHTML {:__html s-rendered}
             :style                   {:overflow-x "auto"
                                       :overflow-y "hidden"}}]
           [:br]
           [c/Button {:on-click #(reset! state s)} "Copy"]]))])])
