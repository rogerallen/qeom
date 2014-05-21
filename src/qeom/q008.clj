;; starting with toon shader here http://www.processing.org/tutorials/pshader/
(ns qeom.q008
  (:require [quil.core :refer :all]))

(defn setup []
  (no-stroke)
  (fill 204)
  (set-state! :toon (load-shader "src/shaders/ToonFrag.glsl"
                            "src/shaders/ToonVert.glsl"))
  (.set (state :toon) "fraction" (float 1.0))) ;; note conversion to float

(defn draw []
  (shader (state :toon))
  (background 0)
  (let [dir-y (* (- (/ (mouse-y) (height)) 0.5) 2)
        dir-x (* (- (/ (mouse-x) (width)) 0.5) 2)]
    (directional-light 204 204 204 (- dir-x) (- dir-y) -1.0)
    (dorun
     (map (fn [[i j]]
            (let [x (* (width) (/ i 5.0))
                  y (* (height) (/ j 5.0))
                  dx (/ (width) (* 2 5.0))
                  dy (/ (height) (* 2 5.0))]
              (push-matrix)
              (translate (+ x dx) (+ y dy))
              (sphere 50)
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
