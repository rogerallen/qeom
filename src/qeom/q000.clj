;; inspiration
;; http://geometrydaily.tumblr.com/post/49358729795/430-camouflage-a-new-minimal-geometric

(ns qeom.q000
  (:use quil.core))

(defn random-int [a b]
  (int (random a b)))

(defn setup []
  (smooth)
  (frame-rate 30)
  (stroke 0 0)
  (stroke-weight 0))

(defn random-fill []
  (case (random-int 0 4)
    0 (fill  28  38  28)
    1 (fill 109 127  94)
    2 (fill  63  68  71)
    3 (fill 220)))

(defn random-xy []
  (* 10 (random-int 0 90)))

(defn draw []
  (background 220)
  (random-seed (+ (day) (int (/ (millis) 10000))))
  (dotimes [i 1000]
    (random-fill)
    (ellipse (random-xy) (random-xy) 90 90))
  (fill 220)
  (rect   0   0 100 900)
  (rect   0   0 900 100)
  (rect 800   0 100 900)
  (rect   0 800 900 100))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [900 900])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
