(ns snake.data)

(defrecord Playing [history length direction food])

(defrecord Crashed [last-state])
