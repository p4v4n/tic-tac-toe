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
    (board/update-board-state board-state engine-move-pick)))

(defn next-board-state [board-state]
  (case (:turn board-state)
    \X (user-moves board-state)
    \O (engine-moves board-state)))

(defn game-play [initial-board-state]
  (loop [board-state initial-board-state]
    (board/console-print (:board board-state))
    (if (board/game-over? (:board board-state))
        (board/end-of-game-action board-state)
        (do (println (str "Player-" (:turn board-state) " to move:"))
            (recur (next-board-state board-state))))))

(defn -main [board-size-str]
  (let [board-length (Integer/parseInt (string/trim board-size-str))]
    (->> (board/create-initial-board-state board-length)
         game-play)))
