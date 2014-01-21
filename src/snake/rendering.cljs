(ns snake.rendering
  (:require [snake.react :as r]
            [snake.data :as data]))

(defn explode-coord [[x y]]
  [(+ 5 (* 10 x))
   (+ 5 (* 10 y))])

(defn snake-segment [[[x1 y1] [x2 y2]]]
  (r/line {:x1 x1 :y1 y1 
           :x2 x2 :y2 y2
           :style {:stroke "rgb(200,0,100)"
                   :strokeWidth 10}}))

(defn food [coord]
  (let [[cx cy] (explode-coord coord)]
    (r/circle {:cx cx :cy cy :r 5 :fill "red"})))

(defn draw-snake-segments [{:keys [history length]}]
  (->> (take length history)
       (map explode-coord)
       (partition 2 1)
       (map snake-segment)))

(defprotocol Render
  (render [state]))


(extend-type data/Playing
  Render
  (render [state]
    (r/svg {:width 210 :height 210}
           (draw-snake-segments state)
           (food (:food state)))))


(extend-type data/Crashed
  Render
  (render [{:keys [last-state]}]
    (r/div {}
           (render last-state)
           (str "You crashed at length " (:length last-state) "."))))
