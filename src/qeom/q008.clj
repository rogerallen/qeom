;; starting with toon shader here http://www.processing.org/tutorials/pshader/
(ns qeom.q008
  (:require [quil.core :refer :all]))

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

(defn setup []
  (no-stroke)
  (set-state! :toon (load-shader "src/shaders/ToonFrag.glsl"
                                 "src/shaders/ToonVert.glsl"))
  (.set (state :toon) "fraction" (float 1.0))) ;; note conversion to float

(defn draw []
  (let [dir-y (* (- (/ (mouse-y) (height)) 0.5) 2)
        dir-x (* (- (/ (mouse-x) (width)) 0.5) 2)
        ms (/ (millis) 10000.0)]
    (shader (state :toon))
    (background 200)
    (random-seed (+ (day) ms))
    (directional-light 204 204 204 (- dir-x) (- dir-y) -1.0)
    (dorun
     (map (fn [[i j]]
            (let [x  (* (width) (/ i 5.0))
                  y  (* (height) (/ j 5.0))
                  dx (/ (width) (* 2 5.0))
                  dy (/ (height) (* 2 5.0))
                  c  (hsv2rgb (random 0.0 1.0) 0.3 0.6)
                  t (fract ms)
                  s (- 1.0 t)
                  s (* s s)
                  r  (random 30 60)
                  r  (+ r (* 10 s (sin (+ x y (* 20 TWO-PI t)))))]
              (push-matrix)
              (translate (+ x dx) (+ y dy))
              (apply fill c)
              (sphere r)
              (pop-matrix)))
          (for [i (range 5) j (range 5)] [i j])))))

(defn run [title]
  (defsketch doodle
    :title    (str title)
    :setup    setup
    :draw     draw
    :size     [900 900]
    :renderer :p3d)
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
