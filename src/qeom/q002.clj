;; inspiration
;; http://geometrydaily.tumblr.com/post/55511678460/484-block-mesh-this-is-479s-sister-piece

(ns qeom.q002
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 0.2)
  (background 220 220 200)
  (stroke 0)
  (stroke-weight 5)
  (fill 200))

(defn draw []
  (background 220 220 200)
  (translate 450 450)
  (rotate (/ PI 4))
  (dotimes [i 100]
    (let [x0 -200
          j (- (rand-int 20) 10)
          y0 (* 20 j)]
      (if (= 0 (rand-int 2))
        (rect x0 y0 400 20)
        (rect y0 x0 20 400)))))

(defn run []
  (defsketch doodle
  :title "q002"
  :setup setup
  :draw draw
  :size [900 900])
  nil)

;;(run)
