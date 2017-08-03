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
  (while true
    (read-and-process-move (read-line))))

(defn -main []
  (game-play))
