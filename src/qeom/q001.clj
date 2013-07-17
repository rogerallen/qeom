;; inspired by
;; http://geometrydaily.tumblr.com/post/54427954169/474-trapped-a-new-minimal-geometric-composition
(ns qeom.q001
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 30))

(defn random-bool []
  (= 1 (int (random 0 2))))

(defn prim
  [x y w rot]
  (let [r (* (Math/sqrt 3) (/ w 2))
        [x0 x1 x2] (map #(+ x (* r (Math/cos (+ rot (/ (* % 2 Math/PI) 3))))) [0 1 2])
        [y0 y1 y2] (map #(+ y (* r (Math/sin (+ rot (/ (* % 2 Math/PI) 3))))) [0 1 2])]
    (triangle x0 y0 x1 y1 x2 y2)))

(defn prims
  [x y]
  (doall
   (doseq [sfn (if (random-bool) [first second] [second first])]
     (let [rot (sfn [0 PI])
           col (sfn [[128 64 52] [166 184 166]])]
       (apply fill col)
       (prim x y 34 rot)))))

(defn draw []
  (background 230)
  (translate 100 100)
  ;;(stroke (random 255))
  (stroke-weight 0)
  (random-seed (int (/ (millis) 10000)))
  (doall
   (map (fn [[i j]] (prims (* i (* (Math/sqrt 3) 26)) (* j 26)))
        (filter (fn [[i j]] (= 1 (mod (+ i j) 2)))
                (for [i (range 16) j (range (dec (* (Math/sqrt 3) 16)))] [i j])))))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [900 900])
  nil)

;;(run (second (clojure.string/split (str *ns*) #"\.")))
