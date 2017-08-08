(ns tic-tac-toe.ai
  (require [tic-tac-toe.board :as board]))

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

;--------Search and Eval

(defn static-evaluation [board-state eval-player]
  (if (board/game-over? (:board board-state))
      ({\X 10 \O -10 \- 0} (board/winner board-state))))
  
;-----------Pick a Move

(defn engine-choice [board-state]
  (->> board-state
       valid-move-list
       count
       rand-int
       (get (valid-move-list board-state))))
