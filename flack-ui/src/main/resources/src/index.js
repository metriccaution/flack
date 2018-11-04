import React from "react";
import ReactDOM from "react-dom";
import { connect, Provider } from "react-redux";
import createStore, { moveMouse, click } from "./redux";
import wsSub from "./websocket-redux";
import MousePad from "./components/mouse/MousePad.jsx";
import MouseButtons from "./components/mouse/MouseButtons.jsx";
import openConnection from "./websocket-connection";
import "./index.css";

const store = createStore();
wsSub(openConnection)(store);

const App = props => {
  return (
    <div
      style={{
        height: "100%"
      }}
    >
      <div
        style={{
          height: "90%"
        }}
      >
        <MousePad
          moveMouse={props.moveMouse}
          tickMillis={props.mouseConfig.millisPerTick}
          maxPerTick={props.mouseConfig.maxPixelsPerTick}
        />
      </div>
      <div
        style={{
          height: "10%"
        }}
      >
        <MouseButtons click={props.click} clicked={props.clicked} />
      </div>
    </div>
  );
};

const Joined = connect(
  state => ({
    mouseConfig: state.mouse.config,
    clicked: state.mouse.click
  }),
  dispatch => ({
    moveMouse: ({ x, y }) => dispatch(moveMouse({ x, y })),
    click: ({ button, down }) => dispatch(click({ button, down }))
  })
)(App);

ReactDOM.render(
  <Provider store={store}>
    <Joined />
  </Provider>,
  document.getElementById("root")
);
