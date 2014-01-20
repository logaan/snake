(ns snake.directions)

(defn north [[x y]] [x (+ y 1)])
(defn south [[x y]] [x (- y 1)])
(defn east  [[x y]] [(+ x 1) y])
(defn west  [[x y]] [(- x 1) y])

