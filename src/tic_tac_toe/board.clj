(ns tic-tac-toe.board)

(def initial-board [[\- \- \-]
                    [\- \- \-]
                    [\- \- \-]])

(def board-state (atom {:board initial-board
                        :turn "X"
                        :game-state :in-progress}))
