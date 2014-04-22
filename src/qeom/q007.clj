;; inspired by
;; http://patakk.tumblr.com/post/83247102946

(ns qeom.q007
  (:use quil.core))

(defn fifo-wr [fifo& x]
  (swap! fifo& (fn [old] (concat old [x]))))

(defn fifo-pop [fifo&]
  (swap! fifo& (fn [old] (rest old))))

(defn fifo-maxlen-wr [fifo& maxlen x]
  (if (>= (count @fifo&) maxlen)
    (fifo-pop fifo&))
  (swap! fifo& (fn [old] (concat old [x]))))

(defn fifo-rd [fifo&]
  (let [rd-v (first @fifo&)]
    (fifo-pop fifo&)
    rd-v))

;;(fifo-wr 5)

(def FIFO-LEN 64)
(def xy& (atom '()))
(def xy-push (partial fifo-maxlen-wr xy& FIFO-LEN))
(def widths
  (for [x (range FIFO-LEN)]
    (+ (* (Math/sin (/ (* x Math/PI) (dec FIFO-LEN))) 48) 2)))

(defn setup []
  (smooth)
  (frame-rate 30))

(defn draw3 [[x y dx dy] w]
  (let [x1 x
        y1 y
        x2 (+ x (* 1.21 dx (- w 8)))
        y2 (+ y (* 1.21 dy (- w 8)))]
    (fill 230)
    (ellipse x1 y1 w w)
    (fill 200)
    (ellipse x2 y2 w w)))

(defn draw []
  (let [ms (/ (millis) 600.0)
        x (* 75 (Math/sin (* 2 ms)))
        y (* 150 (Math/cos (* 1 ms)))
        dx (Math/sin (* 3.5 ms))
        dy (Math/cos (* 3.5 ms))]
    (background 230)
    (stroke-weight 1)
    (stroke 0 255)
    (translate 165 225)
    (xy-push [x y dx dy])
    (dorun (map draw3 @xy& widths))))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [330 460])
  nil)

(comment
  (run (second (clojure.string/split (str *ns*) #"\.")))
)
