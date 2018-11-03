import { reducer } from "./redux";

it("has the expected base state", () => {
  expect(reducer()).toEqual({
    mouse: {
      position: {
        x: 0,
        y: 0
      }
    },
    websocket: {
      config: {
        url: "ws://localhost/control"
      },
      messages: {
        pending: []
      }
    }
  });
});

describe("Messages reducer", () => {
  it("sends a mouse move", () => {
    const base = reducer();

    const afterQueueing = reducer(base, {
      type: "mouse.move",
      x: 5,
      y: 10
    });

    expect(afterQueueing).toEqual({
      mouse: {
        position: {
          x: 5,
          y: 10
        }
      },
      websocket: {
        config: {
          url: "ws://localhost/control"
        },
        messages: {
          pending: [
            {
              id: "0",
              body: {
                type: "mouse.move",
                x: 5,
                y: 10
              }
            }
          ]
        }
      }
    });

    const afterSending = reducer(afterQueueing, {
      type: "sendMessages",
      payload: ["0"]
    });

    expect(afterSending).toEqual({
      mouse: {
        position: {
          x: 5,
          y: 10
        }
      },
      websocket: {
        config: {
          url: "ws://localhost/control"
        },
        messages: {
          pending: []
        }
      }
    });
  });
});
