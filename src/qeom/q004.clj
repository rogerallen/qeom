;; inspiration
;; http://geometrydaily.tumblr.com/post/52379056609/457-park-a-new-minimal-geometric-composition

(ns qeom.q004
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 30))

(defn random-int [a b]
  (int (random a b)))

(defn random-bool []
  (= 1 (int (random 0 2))))

(defn draw []
  (background 230)
  (stroke-weight 7)
  (stroke 20)
  (fill 230 0)
  (random-seed (int (/ (millis) 10000)))
  (loop [i (random-int 3 6)
         x (random-int 400 600)
         y (random-int 200 300)
         d (random-int 300 500)]
    (when (and (> i 0) (< y 730) (> d 20))
      (let [dn (int (* d (+ (/ 0.25 (sq i)) (random 0.4 0.8))))
            pm (if (random-bool) - +)
            xn (pm x (/ dn 2))
            yn (if (random-bool)
                 (int (+ y (sqrt (- (sq (+ (/ d 2) (/ dn 2))) (sq (/ dn 2))))))
                 (int (+ y (sqrt (- (sq (/ d 2)) (sq (/ dn 2)))))))]
        (ellipse x y d d)
        (line x y x 750)
        (recur (dec i) xn yn dn)))))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [900 900])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
