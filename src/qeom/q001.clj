(ns qeom.q001
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 5)
  (background 200))

(defn prim
  [x y w rot]
  (let [r (* (Math/sqrt 3) (/ w 2))
        x0 (+ x (* r (Math/cos (+ rot (/ (* 0 2 Math/PI) 3)))))
        y0 (+ y (* r (Math/sin (+ rot (/ (* 0 2 Math/PI) 3)))))
        x1 (+ x (* r (Math/cos (+ rot (/ (* 1 2 Math/PI) 3)))))
        y1 (+ y (* r (Math/sin (+ rot (/ (* 1 2 Math/PI) 3)))))
        x2 (+ x (* r (Math/cos (+ rot (/ (* 2 2 Math/PI) 3)))))
        y2 (+ y (* r (Math/sin (+ rot (/ (* 2 2 Math/PI) 3)))))]
    (triangle x0 y0 x1 y1 x2 y2)))

(defn draw []
  (stroke (random 255))
  (stroke-weight (random 10))
  (fill (random 255))

  (let [w (random 50 100)
        rot (random Math/PI)
        x (* 10 (random (/ (width) 10)))
        y (* 10 (random (/ (height) 10)))]
    (prim x y w rot)))

(defn run []
  (defsketch doodle
  :title "q001"
  :setup setup
  :draw draw
  :size [900 900])
  nil)
