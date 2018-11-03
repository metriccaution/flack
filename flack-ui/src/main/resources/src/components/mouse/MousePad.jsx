import React from "react";
import { relativise } from "./move-utils";
import "./MousePad.css";

export default class MousePad extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      // Holds the identifier for the setInterval that handles mouse moves
      interval: null,
      // Holds a local copy of the mouse position - Kept up to date with every
      // move event - as opposed to the redux state, which is updated every tick
      position: {
        x: 0,
        y: 0
      }
    };
  }

  componentDidMount() {
    this.setState({
      interval: setInterval(() => {
        if (this.state.position.x !== 0 && this.state.position.y !== 0) {
          this.props.moveMouse(this.state.position);
        }
      }, this.props.tickMillis)
    });
  }

  componentDidUpdate({ tickMillis }) {
    if (tickMillis !== this.props.tickMillis) {
      throw new Error(
        "The MousePad component currently does not support changing the tick millis"
      );
    }
  }

  componentWillUnmount() {
    clearInterval(this.state.interval);
  }

  mouseEvent(event) {
    this.moveMouse(
      {
        x: event.clientX,
        y: event.clientY
      },
      event.target.getBoundingClientRect()
    );
  }

  touchEvent(event) {
    this.moveMouse(
      {
        x: event.touches.item(0).clientX,
        y: event.touches.item(0).clientY
      },
      event.target.getBoundingClientRect()
    );
  }

  resetPosition() {
    this.moveMouse({ x: 0.5, y: 0.5 });
  }

  moveMouse(
    position,
    containerPosition = { top: 0, bottom: 1, left: 0, right: 1 }
  ) {
    const relativePosition = relativise(
      position,
      containerPosition,
      this.props.maxPerTick
    );
    this.setState({
      position: {
        x: Math.floor(relativePosition.x),
        y: Math.floor(relativePosition.y)
      }
    });
  }

  render() {
    return (
      <div
        className="mouse-pad"
        onMouseMove={this.mouseEvent.bind(this)}
        onMouseLeave={this.resetPosition.bind(this)}
        onTouchMove={this.touchEvent.bind(this)}
        onTouchEnd={this.resetPosition.bind(this)}
      />
    );
  }
}
