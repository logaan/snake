(ns snake.core
  (:use [snake.behavior.crashing :only [crash-watcher]]
        [snake.behavior.eating :only [eat-food-watcher]]
        [snake.behavior.moving :only [advance-snake set-direction]] 
        [snake.rendering :only [render]])
  (:require [snake.react :as r]
            [snake.directions :as d]
            [snake.states :as states]))

(def state
  (atom
    (states/map->Playing
      {:history   '([1 3] [1 2] [1 1])
       :length    4 
       :direction d/south
       :food      [10 10]})))

(def component
  (r/create-class
    {:render #(render @state)}))

(let [new-game  (component #js {})
      container (js/document.getElementById "content")
      interval  (js/setInterval (partial advance-snake state) 100)]
  (r/render-component new-game container) 
  (add-watch state :force-update (fn [k r os ns] (.forceUpdate new-game)))
  (add-watch state :eat-food-watcher eat-food-watcher)
  (add-watch state :crash-watcher (partial crash-watcher interval))
  (aset container "onkeydown" (partial set-direction state))
  (.focus container))

