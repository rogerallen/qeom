;; inspired by
;; http://geometrydaily.tumblr.com/post/54342426265/473-planets-spinning-a-new-minimal-geometric
(ns qeom.q005
  (:use quil.core))

(defonce dump-image-count (atom 0))

(defn mix [x y a]
  (+ (* x (- 1.0 a)) (* y a)))

(defn clamp [v lo hi]
  (max lo (min hi v)))

(defn fract [v]
  (- v (int v)))

(defn hsv2rgb
  "given hsv in [0.0,1.0] range, return rgb in [0,255] range"
  [h s v]
  (map #(int (* 255 v (mix 1.0 (clamp (- (abs (- (* 6.0 (fract (+ h (/ % 3.0)))) 3.0)) 1.0) 0.0 1.0) s)))
       [3.0 2.0 1.0]))

(defn random-int [a b]
  (int (random a b)))

(defn setup [rate]
  (smooth)
  (frame-rate rate))
(def setup-normal (partial setup 30))
(def setup-dump   (partial setup 5))

(defn draw []
  (random-seed (+ (day) (minute)))
  (let [ms0 (* (millis) 0.0003)
        rs (random 200 400)
        rs (* (/ rs 900) (width))
        re (random 500 700)
        re (* (/ re 900) (width))
        wo2 (/ (width) 2)
        delta (* (/ 50 900) (width))
        delta2 (* (/ 30 900) (width))]
    (background 230)
    (translate wo2 wo2)
    (fill 255 0)
    (stroke-weight 4)

    (apply stroke (hsv2rgb 0.1 0.5 0.5))
    (ellipse 0 0 rs rs)

    (apply stroke (hsv2rgb 0.5 0.5 0.5))
    (ellipse 0 0 re re)

    ;; don't really need push/pop, but keeping here just in case.
    (push-matrix)

    (rotate (+ ms0 (* 0.333 TWO-PI)))
    (loop [d0 rs d1 re]
      (let [d2 (random-int (+ d0 delta) (/ (+ d0 d1) 2))
            r1 (+ ms0 (* (random 0.0 1.0) PI))
            r2 (+ (/ 10000.0) r1)]
        (when (< (+ d2 delta2) d1)
          (translate (/ (- d2 d0) 2) 0)
          (rotate r1);(/ (+ r1 r2) 2))
          (apply stroke (hsv2rgb (random 0.0 1.0) 0.5 0.25))
          (ellipse 0 0 d2 d2)
          (recur d2 d1))))

    (pop-matrix))
  (when (> @dump-image-count 0)
    (save-frame "q005-dump-####.png")
    (swap! dump-image-count dec)))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup-normal :draw draw :size [900 900])
  nil)

(defn dump [n title]
  (swap! dump-image-count (fn [_] n))
  (defsketch doodle :title (str title) :setup setup-dump :draw draw :size [450 450])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))

;; (found 1-105 worked for 1 animation)
;;(dump 120 (second (clojure.string/split (str *ns*) #"\.")))
