;; inspired by
;; http://patakk.tumblr.com/post/83247102946

(ns qeom.q007
  (:use quil.core))

(defonce dump-image-count (atom 0))
(defonce dump-time (atom 0.0))

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
(def WIDTH-INC 2)
(def WIDTH 48)
(def xy& (atom '()))
(def xy-push (partial fifo-maxlen-wr xy& FIFO-LEN))
(def widths
  (for [x (range FIFO-LEN)]
    (+ (* (Math/sin (/ (* x Math/PI) (dec FIFO-LEN))) WIDTH) WIDTH-INC)))

(defn setup [rate]
  (reset! dump-time 0.0)
  (smooth)
  (frame-rate rate))
(def setup-normal (partial setup 30))
(def setup-dump   (partial setup 5))

(defn draw3 [[x y dx dy] w]
  (let [x1 x
        y1 y
        x2 (+ x (* 1.51 dx (- w 8)))
        y2 (+ y (* 1.51 dy (- w 8)))
        x3 (+ x (* 1.51 dy (- w 8)))
        y3 (+ y (* 1.51 dx (- w 8)))
        ]
    (stroke-weight (* 2 (/ w (+ WIDTH WIDTH-INC))))
    (fill 230)
    (ellipse x1 y1 w w)
    (fill 200)
    (ellipse x2 y2 w w)
    (fill 180)
    (ellipse x3 y3 w w)))

(defn get-millis []
  (if (> @dump-image-count 0)
    (swap! dump-time (fn [t] (+ t (/ 30.0))))
    (/ (millis) 600.0)))

(defn draw []
  (let [ms (get-millis)
        x (* 75 (Math/sin (* 2 ms)))
        y (* 150 (Math/cos (* 1 ms)))
        dx (Math/sin (* 3.5 ms))
        dy (Math/cos (* 3.5 ms))]
    (background 230)
    (stroke 0 255)
    (translate 165 225)
    (xy-push [x y dx dy])
    (dorun (map draw3 @xy& widths)))
  (when (> @dump-image-count 0)
    (save-frame "q007-dump-####.png")
    (swap! dump-image-count dec)))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup-normal :draw draw :size [330 460])
  nil)

(defn dump [n title]
  (swap! dump-image-count (fn [_] n))
  (defsketch doodle :title (str title) :setup setup-dump :draw draw :size [330 460])
  nil)

(comment
  ;; normal run
  (run (second (clojure.string/split (str *ns*) #"\.")))
  ;; or save images (frame 64 starts the animation)
  (do
    (reset! xy& '())
    (dump 500 (second (clojure.string/split (str *ns*) #"\."))))
)
