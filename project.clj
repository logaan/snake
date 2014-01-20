(defproject snake "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "1.0.1"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2138"]]
  :cljsbuild {:builds {:main {:source-paths ["src"]
                              :compiler {:output-to "resources/public/js/main.js"
                                         :optimizations :whitespace
                                         :pretty-print true}}
                       :test {:source-paths ["src" "test"]
                              :compiler {:output-to "resources/public/js/test.js"
                                         :optimizations :whitespace
                                         :pretty-print true}}}})
