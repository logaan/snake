(ns snake.core
  (:require [snake.react :as r]
            [snake.directions :as d]))

(defn log [value]
  (js/console.log (clj->js value))
  value)

(defn explode-coord [[x y]]
  [(* 10 x) (* 10 y)])

(defn snake-segment [[[x1 y1] [x2 y2]]]
  (r/line {:x1 x1 :y1 y1 
           :x2 x2 :y2 y2
           :style {:stroke "rgb(255,0,0)"
                   :strokeWidth 2}}))

(defn history->lines [{:keys [history length]}]
  (->> (take length history)
       (map explode-coord)
       (partition 2 1)
       (map snake-segment)))

(def component
  (r/create-class
    {:getInitialState
     (fn []
       {:history [[1 1] [1 2] [1 3]]
        :length 3
        :direction d/north
        :food [2 2]})
     :render
     (fn []
       (this-as this
                (r/svg {} (history->lines (.-state this)))))}))

(r/render-component (component #js {})
                    (js/document.getElementById "content"))

