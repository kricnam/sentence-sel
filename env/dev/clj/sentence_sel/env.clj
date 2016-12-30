(ns sentence-sel.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [sentence-sel.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[sentence-sel started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[sentence-sel has shut down successfully]=-"))
   :middleware wrap-dev})
