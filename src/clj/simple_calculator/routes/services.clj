(ns simple-calculator.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["thingie"]

    (POST "/calculate" []
      :return       s/Str
      :body-params [string :- s/Str]
      :summary "Calculate and simplify the expression"
      (ok (str string "*1")))))