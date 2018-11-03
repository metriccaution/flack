import { createStore, combineReducers } from "redux";
import Url from "url-parse";

/*
 * Utilities
 */
const baseUrl = new Url(document.location.href, false);

const idGen = (() => {
  let count = 0;
  return () => `${count++}`;
})();

/*
 * Mouse state
 */
function mouseReducer(
  state = {
    position: {
      x: 0,
      y: 0
    }
  },
  action = {}
) {
  switch (action.type) {
    case "mouse.move":
      return Object.assign({}, state, {
        position: { x: action.x, y: action.y }
      });

    default:
      return state;
  }
}

/*
 * Websocket state
 */

export const sendMessages = ids => ({
  type: "sendMessages",
  payload: ids
});

function pendingMessagesReducer(
  state = {
    pending: []
  },
  action = {}
) {
  switch (action.type) {
    // Mark some list of messages as sent
    case "sendMessages":
      return Object.assign({}, state, {
        pending: state.pending.filter(
          msg => action.payload.indexOf(msg.id) === -1
        )
      });

    // Send a mouse move message
    case "mouse.move":
      const newMessage = {
        id: idGen(),
        body: action
      };
      return Object.assign({}, state, {
        pending: state.pending.slice().concat(newMessage)
      });

    default:
      return state;
  }
}

/**
 * Application reducer
 */
export const reducer = combineReducers({
  mouse: mouseReducer,
  websocket: combineReducers({
    config: () => ({
      url: "ws://" + baseUrl.host + "/control"
    }),
    messages: pendingMessagesReducer
  })
});

export default () => createStore(reducer);
