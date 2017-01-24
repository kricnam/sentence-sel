(ns sentence-sel.routes.home
  (:require [sentence-sel.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [mount.core :as mount])
  (:import [java.io File FileInputStream FileOutputStream]))
  

(defn home-page []
  (layout/render
   "index.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn save-webm [filename {:keys [tempfile size]}]
  (try
    (with-open [in (new FileInputStream tempfile)
                out (new FileOutputStream (str "uploads/" filename))]
      (let [source (.getChannel in)
            dest   (.getChannel out)]
        (.transferFrom dest source 0 (.size source))
        (.flush out)
        (.close in)
        (.delete tempfile)
        (->  (str "uploads/" filename) response/ok  (response/content-type "text/plain"))
                  )))
  )

(defn stop []
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (shutdown-agents)
  (System/exit 0)
  )

(def directory (io/file "uploads"))

(defn ls-uploads []
  {
   :status 200
   :body [:h1   (for [i (file-seq directory)] (str (.getName i)))]
   }
  )

(defn download-file [filename]
  (response/file-response (str "uploads/" filename))
  )

(defn exit-page []
  (future (Thread/sleep 2000) (stop))
  {:status 200
   :body "EXIT"}
  )

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/exit" [] (exit-page))
  (GET "/uploads/:filename" [filename] (download-file filename))
  (POST "/save.php" [video-filename video-blob] (save-webm video-filename video-blob))
  )


