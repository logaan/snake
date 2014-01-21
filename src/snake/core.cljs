(ns snake.core
  (:require [snake.react :as r]
            [snake.directions :as d]
            [clojure.string :as s]))

(defn log [value]
  (js/console.log (clj->js value))
  value)

(defn explode-coord [[x y]]
  [(* 10 x) (* 10 y)])

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

(defn debug-view [{:keys [history length food]}]
  (str ":history " (take length history) "\n"
       ":length " length "\n"
       ":food " food))

(def state
  (atom
    {:history '([1 3] [1 2] [1 1])
     :length 20 
     :direction d/south
     :food [7 7]}))

(def component
  (r/create-class
    { :render
     (fn []
       (r/div {}
              (r/div {} (debug-view @state))
              (r/svg {}
                     (draw-snake-segments @state)
                     (food (:food @state)))))}))

(defn advance-snake [state]
  (swap! state
    (fn [{:keys [direction] :as old-state}] 
      (update-in old-state [:history] #(conj % (direction (first %)))))))

(def key->direction
  {38 d/north
   40 d/south
   37 d/west
   39 d/east})

(defn set-direction [state event]
  (swap! state
    (fn [{:keys [direction] :as old-state}]
      (let [new-direction (or (key->direction (.-keyCode event)) direction)]
        (.preventDefault event)
        (assoc old-state :direction new-direction)))))

(let [new-game (component #js {})
      container (js/document.getElementById "content")]
  (r/render-component new-game container) 
  (add-watch state :force-update (fn [_ _ _ _] (.forceUpdate new-game)))
  (aset container "onkeydown" (partial set-direction state))
  (.focus container)
  (js/setInterval (partial advance-snake state) 200))

