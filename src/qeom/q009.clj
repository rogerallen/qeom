;; sketch inspired by this tweet.  https://twitter.com/runemadsen/status/726200375909474304
(ns qeom.q009
  (:use quil.core))

(def W  900)
(def H  900)
(def C  { :x (/ W 2), :y (/ H 2)})
(def R0 (* 0.6 (/ W 2)))
(def R1 (* 0.9 (/ W 2)))
(def N  7)
(def δθ 0.01)
(def π  Math/PI)

(def θ (atom 0.0))

(defn setup []
  (frame-rate 30)
  (smooth)
  (no-fill)
  (stroke-weight 5))

(defn p2r [r a]
  {:x (+ (C :x) (* r (Math/cos a)))
   :y (+ (C :y) (* r (Math/sin a)))})

(defn draw-segment
  [i0]
  (let [i1 (mod (inc i0) N)
        θ0 (/ (* 2 π i0) N);
        θ1 (/ (* 2 π i1) N);
        θ2 (+  @θ θ0)
        θ3 (+ (- @θ) θ1)
        p0 (p2r R0 θ0)
        p1 (p2r R0 θ1)
        p2 (p2r R1 θ2)
        p3 (p2r R1 θ3)]
    (bezier (p0 :x) (p0 :y)  (p2 :x) (p2 :y)  (p3 :x) (p3 :y)  (p1 :x) (p1 :y))))

(defn draw
  []
  (background 245 245 230)
  (dotimes [i N]
    (draw-segment i))
  (swap! θ (fn [x] (+ x δθ))))

(defn run [title]
  (defsketch doodle :title (str title) :setup setup :draw draw :size [W H])
  nil)
