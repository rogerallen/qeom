(ns qeom.core
  (:gen-class))

(defn -main
  "accept a number on the commandline and use that to load and run a sketch."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  ;; run the appropriate sketch
  (let [nstr (if (> (count args) 0)
               (first args)
               "0")
        n    (try (read-string nstr)
                  (catch NumberFormatException e 0))
        f-ns (str "qeom.q" (format "%03d" n))
        _    (try
               (require (symbol f-ns))
               (catch java.io.FileNotFoundException e nil))
        func (resolve (symbol f-ns "run"))]
    (if func
      (func)
      (println (format "unable to find: %s or %s/run" f-ns f-ns)))))
