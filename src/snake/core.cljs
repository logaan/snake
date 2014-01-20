(ns snake.core
  (:require [snake.react :as r]))

(def component
  (r/create-class
    {:render (fn []
               (this-as this
                        (r/svg {}
                               (r/line {:x1 0
                                        :y1 0
                                        :x2 100
                                        :y2 200
                                        :style {:stroke "rgb(255,0,0)"
                                                :strokeWidth 2}}))))}))

(r/render-component (component #js {}) (js/document.getElementById "content"))

