import React from "react";
import ReactDOM from "react-dom";
import { connect, Provider } from "react-redux";
import createStore from "./redux";
import wsSub from "./websocket-redux";
import MousePad from "./mouse/MousePad.jsx";
import { move } from "./mouse/actions";
import websocketConnection from "./websocket-connection";

const store = createStore();
wsSub(websocketConnection)(store);

const App = props => (
  <div>
    <MousePad moveMouse={props.moveMouse} tickMillis={10} maxPerTick={10} />
  </div>
);

const Joined = connect(
  state => ({
    mousePosition: state.mouse.position
  }),
  dispatch => ({
    moveMouse: position => dispatch(move(position))
  })
)(App);

ReactDOM.render(
  <Provider store={store}>
    <Joined />
  </Provider>,
  document.getElementById("root")
);
