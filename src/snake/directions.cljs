(ns snake.directions)

(defn south [[x y]] [x (+ y 1)])
(defn north [[x y]] [x (- y 1)])
(defn east  [[x y]] [(+ x 1) y])
(defn west  [[x y]] [(- x 1) y])

