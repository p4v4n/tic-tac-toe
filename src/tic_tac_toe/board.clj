(ns tic-tac-toe.board)

(def initial-board [[\- \- \-]
                    [\- \- \-]
                    [\- \- \-]])

(def board-state (atom {:board initial-board
                        :turn "X"
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

;---------------------

