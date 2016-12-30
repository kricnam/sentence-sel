(ns sentence-sel.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(def index (atom 0))

(defn jufra []
  (let [db [
            "要对国家法律法规存有敬畏之心"
            "严格遵守国家法律法规规章制度"
            "要对领导长辈存有敬畏之心"
            "慈悲之心要以诚待人与人为善"
            "坚持以人为本、和谐共赢"
            "坚持追求绩效、不断提高"
            "坚持开拓创新、勇于探索"
            "坚持实事求是、诚实守信"
            "坚持认真负责、敢于担当"
            "国政通大家庭的主人翁精神"
            "高效率快节奏的只争朝夕精神"
            "创一流上水平的开拓创新精神"
            "敢于吃苦耐劳的艰苦创业精神"
            "识大体顾大局的无私奉献精神"
            "坚持解决问题、闻过必改"
            "导向性与实用性相结合"
            "定性与定量相结合的工作方法"
            "突出重点与兼顾一般相结合"
            ]
        ind (swap! index inc)
        ]
    (str (inc  (mod ind (count db))) ":"  (get db (mod ind (count db))))
    )
  )


(defapi service-routes
  {:swagger {:ui "/apiui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["thingie"]
    
    (GET "/jufra" []
      :return      String 
      :summary      "selet one sentence for 100 "
      (ok (jufra)))
    
    ))
