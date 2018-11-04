import { reducer, moveMouse, click, sendMessages, mouseReducer } from "./redux";

it("has the expected base state", () => {
  expect(reducer()).toEqual({
    mouse: {
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
        maxPixelsPerTick: 10,
        millisPerTick: 10
      }
    },
    websocket: {
      config: {
        url: "ws://localhost/control"
      },
      messages: {
        nextId: 0,
        pending: []
      }
    }
  });
});

describe("Messages reducer", () => {
  it("sends a mouse move", () => {
    const base = reducer();

    const afterQueueing = reducer(
      base,
      moveMouse({
        x: 5,
        y: 10
      })
    );

    expect(afterQueueing).toEqual({
      mouse: {
        position: {
          x: 5,
          y: 10
        },
        click: {
          left: false,
          right: false,
          middle: false
        },
        config: {
          maxPixelsPerTick: 10,
          millisPerTick: 10
        }
      },
      websocket: {
        config: {
          url: "ws://localhost/control"
        },
        messages: {
          nextId: 1,
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

    const afterSending = reducer(afterQueueing, sendMessages(["0"]));

    expect(afterSending).toEqual({
      mouse: {
        position: {
          x: 5,
          y: 10
        },
        click: {
          left: false,
          right: false,
          middle: false
        },
        config: {
          maxPixelsPerTick: 10,
          millisPerTick: 10
        }
      },
      websocket: {
        config: {
          url: "ws://localhost/control"
        },
        messages: {
          nextId: 1,
          pending: []
        }
      }
    });
  });

  it("sends a mouse click", () => {
    const base = reducer();

    const afterClick = reducer(base, click({ button: "left", down: true }));

    expect(afterClick).toEqual({
      mouse: {
        position: {
          x: 0,
          y: 0
        },
        click: {
          left: true,
          right: false,
          middle: false
        },
        config: {
          maxPixelsPerTick: 10,
          millisPerTick: 10
        }
      },
      websocket: {
        config: {
          url: "ws://localhost/control"
        },
        messages: {
          nextId: 1,
          pending: [
            {
              id: "0",
              body: {
                type: "mouse.click",
                code: 0,
                down: true
              }
            }
          ]
        }
      }
    });

    const afterSending = reducer(afterClick, sendMessages(["0"]));

    expect(afterSending).toEqual({
      mouse: {
        position: {
          x: 0,
          y: 0
        },
        click: {
          left: true,
          right: false,
          middle: false
        },
        config: {
          maxPixelsPerTick: 10,
          millisPerTick: 10
        }
      },
      websocket: {
        config: {
          url: "ws://localhost/control"
        },
        messages: {
          nextId: 1,
          pending: []
        }
      }
    });
  });
});

describe("Mouse reducer", () => {
  it("Handles click-down then click-up", () => {
    const down = mouseReducer(
      mouseReducer(),
      click({
        button: "left",
        down: true
      })
    );

    expect(down).toEqual({
      click: {
        left: true,
        right: false,
        middle: false
      },
      position: {
        x: 0,
        y: 0
      },
      config: {
        maxPixelsPerTick: 10,
        millisPerTick: 10
      }
    });

    const up = mouseReducer(
      down,
      click({
        button: "left",
        down: false
      })
    );

    expect(up).toEqual({
      click: {
        left: false,
        right: false,
        middle: false
      },
      position: {
        x: 0,
        y: 0
      },
      config: {
        maxPixelsPerTick: 10,
        millisPerTick: 10
      }
    });
  });

  it("Handles different sorts of click", () => {
    const left = mouseReducer(
      mouseReducer(),
      click({
        button: "left",
        down: true
      })
    );

    expect(left).toEqual({
      click: {
        left: true,
        right: false,
        middle: false
      },
      position: {
        x: 0,
        y: 0
      },
      config: {
        maxPixelsPerTick: 10,
        millisPerTick: 10
      }
    });

    const right = mouseReducer(
      left,
      click({
        button: "right",
        down: true
      })
    );

    expect(right).toEqual({
      click: {
        left: true,
        right: true,
        middle: false
      },
      position: {
        x: 0,
        y: 0
      },
      config: {
        maxPixelsPerTick: 10,
        millisPerTick: 10
      }
    });

    const middle = mouseReducer(
      right,
      click({
        button: "middle",
        down: true
      })
    );

    expect(middle).toEqual({
      click: {
        left: true,
        right: true,
        middle: true
      },
      position: {
        x: 0,
        y: 0
      },
      config: {
        maxPixelsPerTick: 10,
        millisPerTick: 10
      }
    });
  });
});
