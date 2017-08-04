(ns tic-tac-toe.core
  (require [tic-tac-toe.board :as board]
           [clojure.string :as string])
  (:gen-class))

(defn translate-move [move-str]
  (let [[row-str column-str] (seq (string/trim move-str))
        row (->> [row-str \a]
                 (map int)
                 (reduce -))
        column ((comp dec #(Integer/parseInt %) str) column-str)]
    [row column]))

(defn read-and-process-move [move-str]
  (let [move-index (translate-move move-str)]
  (board/make-move move-index)))

(defn game-play []
  (board/console-print board/initial-board)
  (while (not (board/game-over? (:board @board/board-state)))
    (println (str (:turn @board/board-state)) "to move:")
    (read-and-process-move (read-line)))
  (board/end-of-game-action))

(defn -main []
  (game-play))
