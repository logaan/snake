(ns snake.behavior.eating)

(defn eat-food-watcher [_k reference _os {[head & _] :history food :food}]
  (when (= head food)
    (swap! reference
           #(-> %
                (assoc :food [(rand-int 20) (rand-int 20)])
                (update-in [:length] inc)))))
