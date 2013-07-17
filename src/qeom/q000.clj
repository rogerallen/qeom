;; simple example from quil website
(ns qeom.q000
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 5)
  (background 200))

(defn draw []
  (stroke (random 255))
  (stroke-weight (random 10))
  (fill (random 255))

  (let [diam (random 100)
        x    (random (width))
        y    (random (height))]
    (ellipse x y diam diam)))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [900 900])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
