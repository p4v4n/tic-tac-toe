(ns tic-tac-toe.core
  (require [tic-tac-toe.board :as board]
           [clojure.string :as string])
  (:gen-class))

(defn read-and-process-move [move-str]  
  (let [move-index (board/translate-move move-str)]
    (if (board/is-valid-move? (:board @board/board-state) move-index)
        (board/make-move move-index)
        (println "Invalid Move"))))

(defn game-play []
  (board/console-print (:board @board/board-state))
  (while (not (board/game-over? (:board @board/board-state)))
    (println (str "Player-" (:turn @board/board-state)) "to move:")
    (read-and-process-move (read-line)))
  (board/end-of-game-action))

(defn -main [board-size]
  (board/update-board-size (Integer/parseInt (string/trim board-size)))
  (game-play))
