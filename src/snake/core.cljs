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

(def component
  (r/create-class
    {:getInitialState
     (fn []
       #js {:wrapper
            {:history '([1 3] [1 2] [1 1])
             :length 20 
             :direction d/south
             :food [7 7]}})

     :render
     (fn []
       (this-as this
                (let [state (r/get-state this)]
                  (r/div {}
                         (r/div {} (debug-view state))
                         (r/svg {}
                                (draw-snake-segments state)
                                (food (:food state)))))))}))

(defn advance-snake [component]
  (let [old-state (r/get-state component)
        direction (:direction old-state)
        new-state (update-in old-state [:history] #(conj % (direction (first %))))]
    (r/set-state! component new-state)))

(def key->direction
  {38 d/north
   40 d/south
   37 d/west
   39 d/east})

(defn set-direction [component event]
  (let [old-state     (r/get-state component)
        new-direction (or (key->direction (.-keyCode event))
                          (:direction old-state))
        new-state     (assoc old-state :direction new-direction)]
    (.preventDefault event)
    (r/set-state! component new-state)))

(let [new-game (component #js {})
      container (js/document.getElementById "content")]
  (r/render-component new-game container) 
  (aset container "onkeydown" (partial set-direction new-game))
  (.focus container)
  (js/setInterval (partial advance-snake new-game) 200))

