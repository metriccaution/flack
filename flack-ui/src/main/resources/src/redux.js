import { createStore, combineReducers } from "redux";
import Url from "url-parse";

/*
 * Utilities
 */
const baseUrl = new Url(document.location.href, false);

/*
 * Mouse state
 */
const mouseActions = {
  move: "move",
  click: "click"
};

export function moveMouse({ x, y }) {
  return {
    type: mouseActions.move,
    x,
    y
  };
}

export function click({ down, button }) {
  return {
    type: mouseActions.click,
    down,
    button
  };
}

export function mouseReducer(
  state = {
    position: {
      x: 0,
      y: 0
    },
    click: {
      left: false,
      right: false,
      middle: false
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

    case mouseActions.click:
      const diff = {};
      diff[action.button] = action.down;
      const clickState = Object.assign({}, state.click, diff);
      return Object.assign({}, state, { click: clickState });

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

function convertClickType(name) {
  switch (name) {
    case "left":
      return 0;
    case "right":
      return 1;
    case "middle":
      return 2;
    default:
      throw new Error(`Unrecognised click code ${name}`);
  }
}

function pendingMessagesReducer(
  state = {
    nextId: 0,
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
      const newMoveMessage = {
        id: `${state.nextId++}`,
        body: {
          type: "mouse.move",
          x: action.x,
          y: action.y
        }
      };
      return Object.assign({}, state, {
        pending: state.pending.concat(newMoveMessage)
      });

    case mouseActions.click:
      const newClickMessage = {
        id: `${state.nextId++}`,
        body: {
          type: "mouse.click",
          code: convertClickType(action.button),
          down: Boolean(action.down)
        }
      };
      return Object.assign({}, state, {
        pending: state.pending.concat(newClickMessage)
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
