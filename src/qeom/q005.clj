;; inspired by
;; http://geometrydaily.tumblr.com/post/54342426265/473-planets-spinning-a-new-minimal-geometric
(ns qeom.q005
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 30))

(defn mix [x y a]
  (+ (* x (- 1.0 a)) (* y a)))

(defn clamp [v lo hi]
  (max lo (min hi v)))

(defn fract [v]
  (- v (int v)))

(defn hsv2rgb
  "given hsv in [0.0,1.0] range, return rgb in [0,255] range"
  [h s v]
  (map #(int (* 255 (* v (mix 1.0 (clamp (- (abs (- (* 6.0 (fract (+ h (/ % 3.0)))) 3.0)) 1.0) 0.0 1.0) s))))
       [3.0 2.0 1.0]))

(defn draw []
  (let [ms0 (/ (millis) 10000.0)]
    (random-seed (int ms0))
    (background 230)
    (translate 450 450)
    (fill 255 0)
    (stroke-weight 3)

    (apply stroke (hsv2rgb 0.5 0.5 0.5))
    (ellipse 0 0 100 100)

    (push-matrix)

    ;; FIXME Work in Progress
    (rotate (+ ms0 (* 0.333 TWO-PI)))
    (translate 25 0)
    (rotate (+ ms0 (* 0.333 TWO-PI)))
    (apply stroke (hsv2rgb 0.1 0.5 0.5))
    (ellipse 0 0 150 150)

    (translate -75 0)
    (rotate (+ ms0 (* 0.333 TWO-PI)))
    (apply stroke (hsv2rgb 0.3 0.5 0.5))
    (ellipse 0 0 300 300)

    (translate 75 0)
    (rotate (+ ms0 (* 0.333 TWO-PI)))
    (apply stroke (hsv2rgb 0.7 0.5 0.5))
    (ellipse 0 0 450 450)

    (pop-matrix)

    (apply stroke (hsv2rgb 0.9 0.5 0.5))
    (ellipse 0 0 500 500)))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [900 900])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
