(ns snake.behavior.crashing
  (:use [snake.data :only [Crashed]]))

(defn crash-watcher [interval _k reference _os {:keys [history length] :as ns}]
  (let [[head & tail] (take length history)
        [x y]         head]
    (when (or (not (and (<= 0 x 20) (<= 0 y 20)))
              (some #{head} tail))
      (js/clearInterval interval)
      (remove-watch reference :eat-food-watcher)
      (remove-watch reference :crash-watcher)
      (reset! reference (Crashed. ns)))))
