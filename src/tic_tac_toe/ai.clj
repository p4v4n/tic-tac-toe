(ns tic-tac-toe.ai)

(defn move-indices-for-board [n]
  (for [x (range n)
        y (range n)]
    [x y]))

(defn valid-move-list [board-state]
  (->> (:board board-state)
       flatten
       (map vector (move-indices-for-board (:board-size board-state)))
       (filter #(= \- (second %)))
       (mapv first)))

(defn engine-choice [board-state]
  (->> board-state
       valid-move-list
       count
       rand-int
       (get (valid-move-list board-state))))
