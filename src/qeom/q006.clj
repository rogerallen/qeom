;; inspired by
;; http://geometrydaily.tumblr.com/post/51642495789/450-pupils-of-the-world-a-new-minimal-geometric

(ns qeom.q006
  (:use quil.core))

(defn mix [x y a]
  (+ (* x (- 1.0 a)) (* y a)))

(defn clamp [v lo hi]
  (max lo (min hi v)))

(defn fract [v]
  (- v (int v)))

; .0  .1  .2  .3  .4  .5  .6  .7  .8 .9
; r   o   yg  g   gb  b   i   iv  v  m
(defn hsv2rgb
  "given hsv in [0.0,1.0] range, return rgb in [0,255] range"
  [h s v]
  (map #(int (* 255 v (mix 1.0 (clamp (- (abs (- (* 6.0 (fract (+ h (/ % 3.0)))) 3.0)) 1.0) 0.0 1.0) s)))
       [3.0 2.0 1.0]))

(defn random-int [a b]
  (int (random a b)))

(defn setup []
  (smooth)
  (frame-rate 30))

(defn draw1 [ms ij]
  (let [[x y] (map #(* % 200) ij)
        d3 (random-int 4 18)
        d2 (random-int 3 (dec d3))
        d1 (random-int 2 (dec d2))
        [d1 d2 d3] (map #(* 10 %) [d1 d2 d3])
        t (fract ms)
        s (- 1.0 t)
        s (* s s)
        [d1 d2 d3] (map #(+ % (* 10 s (sin (+ x y (* 20 TWO-PI t))))) [d1 d2 d3])
        c1 (hsv2rgb (random 0.0 1.0) 0.1 0.1)
        c2 (hsv2rgb (random 0.0 0.4) 0.3 0.3)
        c3 (hsv2rgb (random 0.1 0.5) 0.6 0.6)]
    (apply fill c3)
    (ellipse x y d3 d3)
    (apply fill c2)
    (ellipse x y d2 d2)
    (apply fill c1)
    (ellipse x y d1 d1)))

(defn draw []
  (let [ms (/ (millis) 10000.0)]
    (random-seed (+ (day) ms))
    (background 230)
    (stroke-weight 0)
    (stroke 0 0)
    (translate 150 150)
    (dorun
     (map (partial draw1 ms)
          (for [i (range 4) j (range 4)] [i j])))))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [900 900])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
