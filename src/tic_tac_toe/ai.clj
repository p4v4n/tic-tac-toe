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

;--------Search and Eval------

(defn static-evaluation [board-state]
  (if (board/game-over? (:board board-state))
      ({\X 10 \O -10 \- 0} (board/winner board-state))))

(defn evaluation-at-depth [board-state]
  (if (board/game-over? (:board board-state))
      (static-evaluation board-state)
      (->> (valid-move-list board-state)
           (map #(board/update-board-state board-state %))
           (map evaluation-at-depth)
           (apply (if (= \X (:turn board-state))
                       max
                       min)))))

(println (evaluation-at-depth {:board [[\X \O \X]
                                       [\X \O \O]
                                       [\- \- \-]]
                              :board-size 3
                              :turn \X
                              :game-state :in-progress}))
  
;-----------Pick a Move

(defn engine-choice [board-state]
  (->> board-state
       valid-move-list
       count
       rand-int
       (get (valid-move-list board-state))))

(defn engine-choice2 [board-state]
  (let [move-list (valid-move-list board-state)
        board-pos-after-moves (map #(board/update-board-state board-state %) move-list)
        board-eval-after-moves (map evaluation-at-depth board-pos-after-moves)]
    (->> (map vector move-list board-eval-after-moves)
         (sort-by second)
         ((if (= \X (:turn board-state)) last first))
         first)))
