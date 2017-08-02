(ns tic-tac-toe.core
  (require [tic-tac-toe.board :as board])
  (:gen-class))

(defn read-and-process-move [move-str]
  (println "dummy body"))

(defn game-play []
  (while true
    (read-and-process-move (read-line))))

(defn -main []
    (game-play))
