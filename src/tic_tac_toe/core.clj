(ns tic-tac-toe.core
  (require [tic-tac-toe.board :as board]
           [tic-tac-toe.ai :as ai]
           [clojure.string :as string])
  (:gen-class))

(defn user-moves [board-state]
  (let [move-index (board/translate-move (read-line))]
    (if (board/is-valid-move? (:board board-state) move-index)
        (board/update-board-state board-state move-index)
        (do (println "Invalid Move")
            board-state))))

(defn engine-moves [board-state]
  (let [engine-move-pick (ai/engine-choice board-state)]
    (Thread/sleep 1000)
    (board/update-board-state board-state engine-move-pick)))

(def player-to-call {"human" user-moves "engine" engine-moves})

(defn next-board-state [board-state player-to-move]
 ((player-to-call player-to-move) board-state))

(defn game-play [initial-board-state player1 player2]
  (loop [board-state initial-board-state player-to-move player1]
    (board/console-print (:board board-state))
    (if (board/game-over? (:board board-state))
        (board/end-of-game-action board-state)
        (do (println (str "Player-" (:turn board-state) " to move:" "(" player-to-move ")"))
            (recur (next-board-state board-state player-to-move)
                   ((zipmap [player1 player2] [player2 player1]) player-to-move))))))

(defn -main [board-size-str player1 player2]
  (let [board-length (Integer/parseInt (string/trim board-size-str))]
    (-> (board/create-initial-board-state board-length)
         (game-play player1 player2))))
