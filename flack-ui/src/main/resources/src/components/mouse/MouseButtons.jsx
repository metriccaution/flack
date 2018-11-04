import React from "react";
import "./MouseButtons.css";

const MouseButton = ({ button, click, down }) => (
  <span
    className={down ? "mouse-button mouse-button__down" : "mouse-button"}
    onMouseDown={() => click(true)}
    onTouchStart={() => click(true)}
    onMouseUp={() => click(false)}
    onTouchEnd={() => click(false)}
  />
);

export default ({ click, clicked }) => {
  const buttons = ["left", "middle", "right"].map(b => (
    <MouseButton
      key={b}
      button={b}
      click={down =>
        click({
          button: b,
          down
        })
      }
      down={clicked[b]}
    />
  ));

  return <div className="mouse-button-container">{buttons}</div>;
};
