import { relativise } from "./move-utils";

describe("Scaling mouse move", () => {
  it("scales to 0 when there is 0 max value", () => {
    expect(
      relativise(
        {
          x: 0,
          y: 0
        },
        {
          left: -1 * Math.random(),
          right: Math.random(),
          top: -1 * Math.random(),
          bottom: Math.random()
        },
        0
      )
    ).toEqual({ x: 0, y: 0 });
  });

  it("Returns the max value at each edge of the bounding box", () => {
    expect(
      relativise(
        {
          x: 2,
          y: 0
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: 3, y: 0 });

    expect(
      relativise(
        {
          x: -2,
          y: 0
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: -3, y: 0 });

    expect(
      relativise(
        {
          x: 0,
          y: 2
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: 0, y: 3 });

    expect(
      relativise(
        {
          x: 0,
          y: -2
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: 0, y: -3 });
  });

  it("Returns the max value outside of the bounding box", () => {
    expect(
      relativise(
        {
          x: 3,
          y: 0
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: 3, y: 0 });

    expect(
      relativise(
        {
          x: -3,
          y: 0
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: -3, y: 0 });

    expect(
      relativise(
        {
          x: 0,
          y: 3
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: 0, y: 3 });

    expect(
      relativise(
        {
          x: 0,
          y: -3
        },
        {
          left: -2,
          right: 2,
          top: -2,
          bottom: 2
        },
        3
      )
    ).toEqual({ x: 0, y: -3 });
  });

  it("Scales values", () => {
    const x = Math.random() * 2 - 1;
    const y = Math.random() * 2 - 1;
    expect(
      relativise(
        {
          x,
          y
        },
        {
          left: -1,
          right: 1,
          top: -1,
          bottom: 1
        },
        2
      )
    ).toEqual({ x: x * 2, y: 2 * y });
  });
});
