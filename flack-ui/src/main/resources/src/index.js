import React from "react";
import ReactDOM from "react-dom";
import { connect, Provider } from "react-redux";
import createStore, { moveMouse } from "./redux";
import wsSub from "./websocket-redux";
import MousePad from "./components/mouse/MousePad.jsx";
import openConnection from "./websocket-connection";

const store = createStore();
wsSub(openConnection)(store);

const App = props => {
  return (
    <div>
      <MousePad
        moveMouse={props.moveMouse}
        tickMillis={props.mouseConfig.millisPerTick}
        maxPerTick={props.mouseConfig.maxPixelsPerTick}
      />
    </div>
  );
};

const Joined = connect(
  state => ({
    mouseConfig: state.mouse.config
  }),
  dispatch => ({
    moveMouse: ({ x, y }) => dispatch(moveMouse({ x, y }))
  })
)(App);

ReactDOM.render(
  <Provider store={store}>
    <Joined />
  </Provider>,
  document.getElementById("root")
);
