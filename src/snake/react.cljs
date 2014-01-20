(ns snake.react)

(defn svg [options & children]
  (js/React.DOM.svg (clj->js options) children))

(defn line [options]
  (js/React.DOM.line (clj->js options)))

(defn create-class [functions]
  (js/React.createClass (clj->js functions)))

(defn render-component [component node]
  (js/React.renderComponent component node))

