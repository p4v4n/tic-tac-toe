(ns tic-tac-toe.board)

(def initial-board [[\- \- \-]
                    [\- \- \-]
                    [\- \- \-]])

(def board-state (atom {:board initial-board
                        :turn \X
                        :game-state :in-progress}))

;----------Printing at Terminal---------

(def char-map {\- " " \X "X" \O "O"})

(def space-line "   |   |   ")

(def divider-line "---|---|---")

(defn line-convertor [line-vec]
  (->> line-vec
       (map char-map)
       (interpose " | ")
       (apply str)
       (#(str " " % " "))))

(defn console-print [board-vec]
  (->> board-vec
       (map line-convertor)
       (interpose divider-line)
       (map #(str % "\n"))
       (apply str)
       (#(str space-line "\n" % space-line "\n"))
       println))

;-----------Making a Move-------

(defn board-pos-after-move [current-board [row column]]
  (-> current-board
    (assoc-in [row column] (:turn @board-state))))

(defn make-move [move-position]
  (let [next-pos (board-pos-after-move (:board @board-state) move-position)]
    (swap! board-state assoc :board next-pos))
  (swap! board-state assoc :turn ({\X \O \O \X} (:turn @board-state)))
  (console-print (:board @board-state)))

;--------------End of Game-------

(defn column-list [board-vec]
  (apply map vector board-vec))

(defn left-diagonal [board-vec]
  (->> board-vec
       (#(map vector % (range)))
       (map #(apply nth %))))

(defn right-diagonal [board-vec]
  (->> (reverse board-vec)
       (#(map vector % (range)))
       (map #(apply nth %))))

(defn list-of-lines [board-vec]
  (concat board-vec (column-list board-vec)) 
          (left-diagonal board-vec) (right-diagonal board-vec))
