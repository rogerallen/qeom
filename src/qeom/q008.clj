;; starting with toon shader here http://www.processing.org/tutorials/pshader/
(ns qeom.q008
  (:require [quil.core :refer :all]))

(defonce toon (atom nil))

(defn setup []
  (no-stroke)
  (fill 204)
  (reset! toon (load-shader "src/shaders/ToonFrag.glsl"
                            "src/shaders/ToonVert.glsl"))
  (.set @toon "fraction" (float 1.0))) ;; note conversion to float

(defn draw []
  (shader @toon)
  (background 0)
  (let [dir-y (* (- (/ (mouse-y) (height)) 0.5) 2)
        dir-x (* (- (/ (mouse-x) (width)) 0.5) 2)]
    (directional-light 204 204 204 (- dir-x) (- dir-y) -1.0)
    (translate (/ (width) 2) (/ (height) 2))
    (sphere 120)))

(defn run [title]
  (defsketch doodle
    :title    (str title)
    :setup    setup
    :draw     draw
    :size     [900 900]
    :renderer :p3d)
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
