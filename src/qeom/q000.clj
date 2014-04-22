;; inspiration
;; http://geometrydaily.tumblr.com/post/49358729795/430-camouflage-a-new-minimal-geometric

(ns qeom.q000
  (:use quil.core))

(set! *warn-on-reflection* true)

(defn fract [v]
  (- v (int v)))

(defn random-long ^long [a b]
  (long (random a b)))

(defn random-bool []
  (= 1 (int (random 0 2))))

(defn setup []
  (smooth)
  (frame-rate 30)
  (stroke 0 0)
  (stroke-weight 0))

(defn random-fill []
  (case (random-long 0 4)
    0 (fill  28  38  28)
    1 (fill 109 127  94)
    2 (fill  63  68  71)
    3 (fill 220)))

(defn random-xy [d]
  (let [rf (if (random-bool) + -)]
    (rf (* 10 (random-long 0 90)) d)))

(defn draw []
  (let [ms (/ (millis) 10000)
        t (- 1.0 (fract ms))
        t (* t t)
        d (+ 90 (* 25 t))]
    (background 220)
    (random-seed (+ (day) ms))
    (dotimes [i 700]
      (random-fill)
      (ellipse (random-xy d) (random-xy d) d d))
    (fill 220)
    (rect   0   0 100 900)
    (rect   0   0 900 100)
    (rect 800   0 100 900)
    (rect   0 800 900 100)))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [900 900])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
