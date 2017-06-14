(ns todo.app
    (:require [rum.core :as rum]))

(rum/defc label [text]
  [:div
   [:h1 "A label hello 111"]
   [:p text]])

(defn init []
  (rum/mount (label) (. js/document (getElementById "container"))))
