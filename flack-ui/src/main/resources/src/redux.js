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
const mouseActions = {
  move: "move"
};

export function moveMouse({ x, y }) {
  return {
    type: mouseActions.move,
    x,
    y
  };
}

function mouseReducer(
  state = {
    position: {
      x: 0,
      y: 0
    },
    config: {
      millisPerTick: 10,
      maxPixelsPerTick: 10
    }
  },
  action = {}
) {
  switch (action.type) {
    case mouseActions.move:
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
const websocketActions = {
  sendMessages: "sendMessages"
};

export const sendMessages = ids => ({
  type: websocketActions.sendMessages,
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
    case websocketActions.sendMessages:
      return Object.assign({}, state, {
        pending: state.pending.filter(
          msg => action.payload.indexOf(msg.id) === -1
        )
      });

    // Send a mouse move message
    case mouseActions.move:
      const newMessage = {
        id: idGen(),
        body: {
          type: "mouse.move",
          x: action.x,
          y: action.y
        }
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
