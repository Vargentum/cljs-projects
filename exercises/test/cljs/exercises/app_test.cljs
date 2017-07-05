(ns exercises.app-test
  (:require-macros [cljs.test :refer [deftest testing is]])
  (:require [cljs.test :as t]
            [exercises.app :as app]))

(deftest test-parse-html-color
  (is (= (app/parse-html-color "#80FFA0")   {:r 128 :g 255 :b 160}))
  (is (= (app/parse-html-color "#3B7")      {:r 51  :g 187 :b 119}))
  (is (= (app/parse-html-color "LimeGreen") {:r 50  :g 205 :b 50}))
)