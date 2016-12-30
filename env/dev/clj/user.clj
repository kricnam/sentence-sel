(ns user
  (:require [mount.core :as mount]
            sentence-sel.core))

(defn start []
  (mount/start-without
                                        ;#'sentence-sel.core/http-server
                       #'sentence-sel.core/repl-server))

(defn stop []
  (mount/stop-except
   ;#'sentence-sel.core/http-server
                     #'sentence-sel.core/repl-server))

(defn restart []
  (stop)
  (start))


