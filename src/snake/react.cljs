(ns snake.react)

(defn svg [options & children]
  (js/React.DOM.svg (clj->js options) (clj->js children)))

(defn line [options]
  (js/React.DOM.line (clj->js options)))

(defn circle [options]
  (js/React.DOM.circle (clj->js options)))

(defn div [options & children]
  (js/React.DOM.div (clj->js options) (clj->js children)))

(defn create-class [functions]
  (js/React.createClass (clj->js functions)))

(defn render-component [component node]
  (js/React.renderComponent component node))

