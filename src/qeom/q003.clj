;; inspiration
;; http://geometrydaily.tumblr.com/post/54913997818/479-mesh-block-a-new-minimal-geometric

(ns qeom.q003
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 30)
  (background 200)
  (stroke-weight 5)
  (stroke 200)
  (fill 0))

(defn draw []
  (background 200)
  (translate 450 450)
  (rotate (/ PI 4))
  (random-seed (int (/ (millis) 10000)))
  (dotimes [i (* 20 6)]
    (let [x0 -200
          j (- (int (random 20)) 10)
          y0 (* 20 j)]
      (if (= 0 (int (random 2)))
        (rect x0 y0 400 20)
        (rect y0 x0 20 400)))))

(defn run []
  (defsketch doodle
  :title (second (clojure.string/split (str *ns*) #"\."))
  :setup setup
  :draw draw
  :size [900 900])
  nil)

;;(run)
