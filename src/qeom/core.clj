(ns qeom.core
  (:gen-class))

(defn -main
  "accept a number on the commandline and use that to load and run a sketch."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  ;; run the appropriate sketch
  (let [nstr     (if (pos? (count args))
                   (first args)
                   (str (rand-int 8))) ;; <-- ADJUST WHEN ADDING NEW SKETCHES
        n        (try (read-string nstr)
                      (catch NumberFormatException e 0))
        n-ns-str (format "q%03d" n)
        f-ns     (str "qeom." n-ns-str)
        _        (try
                   (require (symbol f-ns))
                   (catch java.io.FileNotFoundException e nil))
        func     (resolve (symbol f-ns "run"))]
    (if func
      (do
        (println f-ns)
        (func n-ns-str))
      (println (format "unable to find: %s or %s/run" f-ns f-ns)))))
