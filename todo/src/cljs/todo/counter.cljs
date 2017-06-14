(ns todo.counter
    (:require [rum.core :as rum]
              [scrum.core :as scrum]))

; define controller & event handlers

(def initial-state 0)

(defmulti control (fn [event] event))

(defmethod control :init []
  {:local-storage
   {:method :get
    :key :counter
    :on-read :init-ready}})

(defmethod control :init-ready [_ [counter]]
  (if-not (nil? counter)
    {:state counter}
    {:state initial-state}))

(defmethod control :inc [_ _ counter]
  (let [next-counter (inc counter)]
    {:state next-counter
     :local-storage
     {:method :set
      :data next-counter
      :key :counter}}))

(defmethod control :dec [_ _ counter]
  (let [next-counter (dec counter)]
    {:state next-counter
     :local-storage
     {:method :set
      :data next-counter
      :key :counter}}))


; define effect handler

(defn local-storage [reconciler controller-name effect]
  (let [{:keys [method data key on-read]} effect]
    (case method
      :set (js/localStorage.setItem (name key) data)
      :get (->> (js/localStorage.getItem (name key))
                (scrum/dispatch reconciler controller-name on-read))
      nil)))

; define UI

(rum/defc Counter < rum/reactive [r]
  [:div 
   [:button {:on-click #(scrum/dispatch! r :counter :dec)} "-"]
   [:span (rum/react (scrum/subscription r [:counter]))]
   [:button {:on-click #(scrum/dispatch! r :counter :inc)} "+"]])


; create Reconciler instance

(defonce reconciler
  (scrum/reconciler
    {:state
     (atom {}) ;; application state
     :controllers
     {:counter control} ;; controllers
     :effect-handlers
     {:local-storage local-storage}})) ;; effect 
