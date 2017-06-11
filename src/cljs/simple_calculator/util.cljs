(ns simple-calculator.util)

(def preview-id "preview")
(def render-area-id "render-area")

(defn- update-preview! [string]
  (let [jax (get (js/MathJax.Hub.getAllJax preview-id) 0)]
    (js/MathJax.Hub.Queue (array "Text" jax string))))

(defn set-up-mathjax! []
  (js/MathJax.Hub.Config (js-obj "messageStyle" "none"))
  (set! js/MathJax.Hub.processSectionDelay 0))