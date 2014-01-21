(ns snake.states)

(defrecord Playing [history length direction food])

(defrecord Crashed [last-state])
