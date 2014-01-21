(ns snake.core
  (:require [snake.react :as r]
            [snake.directions :as d]
            [snake.data :as data]
            [snake.rendering :as rendering]))

(defn log [value]
  (js/console.log (clj->js value))
  value)

(def state
  (atom
    (data/map->Playing
      {:history   '([1 3] [1 2] [1 1])
       :length    4 
       :direction d/south
       :food      [20 20]})))

(def component
  (r/create-class
    {:render
     #(rendering/render @state)}))

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

(defn eat-food-watcher [_k reference _os {[head & _] :history food :food}]
  (when (= head food)
    (swap! reference
           #(-> %
                (assoc :food [(rand-int 20) (rand-int 20)])
                (update-in [:length] inc)))))

(defn crash-watcher [interval _k reference _os {:keys [history length] :as ns}]
  (let [[head & tail] (take length history)
        [x y]         head]
    (when (or (not (and (<= 0 x 20) (<= 0 y 20)))
              (some #{head} tail))
      (js/clearInterval interval)
      (remove-watch state :eat-food-watcher)
      (remove-watch state :crash-watcher)
      (reset! reference (data/Crashed. ns)))))

(let [new-game  (component #js {})
      container (js/document.getElementById "content")
      interval  (js/setInterval (partial advance-snake state) 100)]
  (r/render-component new-game container) 
  (add-watch state :force-update (fn [k r os ns] (.forceUpdate new-game)))
  (add-watch state :eat-food-watcher eat-food-watcher)
  (add-watch state :crash-watcher (partial crash-watcher interval))
  (aset container "onkeydown" (partial set-direction state))
  (.focus container))

