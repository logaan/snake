(ns snake.behavior.moving
  (:require [snake.directions :as d]))

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

