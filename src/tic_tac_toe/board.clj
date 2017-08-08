(ns tic-tac-toe.board
  (require [clojure.string :as string]))

;---------Create board-state based on board-size-------

(defn create-initial-board-state [n] {:board (->> (repeat n \-)
                                          vec
                                          (repeat n)
                                          vec)
                              :board-size n
                              :turn \X
                              :game-state :in-progress})

;----------Printing at Terminal---------

(def char-map {\- " " \X "X" \O "O"})

(defn space-line [board-size]
  (->> (repeat board-size "   ")
       (interpose "|")
       (apply str)))

(defn divider-line [board-size]
  (->> (repeat board-size "---")
       (interpose "|")
       (apply str)))

(defn line-convertor [line-vec]
  (->> line-vec
       (map char-map)
       (interpose " | ")
       (apply str)
       (#(str " " % " "))))

(defn console-print [board-vec]
  (->> board-vec
       (map line-convertor)
       (interpose (divider-line (count board-vec)))
       (map #(str % "\n"))
       (apply str)
       (#(str (space-line (count board-vec)) "\n" % 
              (space-line (count board-vec)) "\n"))
       println))

;--------------Convert Valid user-input to move index---------

(defn translate-move [move-str]
  (let [[row-str column-str] (seq (string/trim move-str))
        row (->> [row-str \a]
                 (map int)
                 (reduce -))
        column ((comp dec #(Integer/parseInt %) str) column-str)]
    [row column]))

;------Validity Check--------

(defn is-empty-slot? [move-index board-vec]
  (= \- (->> move-index
             (get-in board-vec))))

(defn is-valid-move? [board-vec move-index]
  (and (and (<= 0 (first move-index) (dec (count board-vec)))
            (<= 0 (last move-index) (dec (count board-vec)))) 
       (is-empty-slot? move-index board-vec)))

;--------------Checking End of Game-------

(defn column-list [board-vec]
  (apply map vector board-vec))

(defn left-diagonal [board-vec]
  (->> board-vec
       (#(map vector % (range)))
       (mapv #(apply nth %))))

(defn right-diagonal [board-vec]
  (->> (reverse board-vec)
       (#(map vector % (range)))
       (mapv #(apply nth %))))

(defn list-of-lines [board-vec]
  (concat board-vec (column-list board-vec) 
          (vector (left-diagonal board-vec) (right-diagonal board-vec))))

(defn only-one-item? [some-vec]
  (if (= 1 ((comp count set) some-vec))
      (first some-vec)))

(defn player-won? [board-vec]
  (->> (list-of-lines board-vec)
       (map only-one-item?)
       (remove nil?)
       (some #(not= \- %))))

(defn any-empty-slot? [board-vec]
  (->> board-vec
       (map #(some #{\-} %))
       (some #((complement nil?) %))))

(defn game-over? [board-vec]
  (or (player-won? board-vec) (not (any-empty-slot? board-vec))))

;-----------Making a Move-------

(defn board-pos-after-move [current-board [row column] player-to-move]
  (-> current-board
    (assoc-in [row column] player-to-move)))

(defn update-board-state [board-state move-position]
  (let [next-pos (board-pos-after-move (:board board-state) move-position (:turn board-state))]
    (-> board-state
        (assoc :board next-pos)
        (assoc :turn ({\X \O \O \X} (:turn board-state))))))

;------------Winner-----

(defn winner [board-state]
  (if (player-won? (:board board-state))
      ({\X \O \O \X} (:turn board-state))
      \-))

;-------End of Game Action-------

(defn end-of-game-action [board-state]
  (let [who-won (winner board-state)]
    (if (not= who-won \-)
        (println (str "Player-" who-won " won"))
        (println "Game ends in draw"))))
