(ns tic-tac-toe.core
  (require [tic-tac-toe.board :as board]
           [clojure.string :as string])
  (:gen-class))

(defn read-and-process-move [move-str] 
  (if (board/is-valid-move? (:board @board/board-state) move-str)
      (let [move-index (board/translate-move move-str)]
        (board/make-move move-index))
      (println "Invalid Move")))

(defn game-play []
  (board/console-print board/initial-board)
  (while (not (board/game-over? (:board @board/board-state)))
    (println (str "Player-" (:turn @board/board-state)) "to move:")
    (read-and-process-move (read-line)))
  (board/end-of-game-action))

(defn -main []
  (game-play))
