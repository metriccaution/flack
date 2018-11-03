import { createStore, combineReducers } from "redux";
import mouseReducer from "./mouse/reducer";
import websockerReducer from "./ws/reducer";

export default () =>
  createStore(
    combineReducers({
      mouse: mouseReducer,
      websocket: websockerReducer
    })
  );
