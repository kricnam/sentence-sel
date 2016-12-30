(ns sentence-sel.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[sentence-sel started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[sentence-sel has shut down successfully]=-"))
   :middleware identity})
