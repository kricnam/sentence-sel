(ns sentence-sel.routes.home
  (:require [sentence-sel.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io])
  (:import [java.io File FileInputStream FileOutputStream])
  )

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
        {
         :status 200
         :body (str "uploads/" filename)
         })))
  )

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (POST "/save.php" [video-filename video-blob] (save-webm video-filename video-blob))
  )


