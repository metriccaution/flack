import React from "react";

const style = {
  height: "400px",
  width: "400px",
  backgroundColor: "chartreuse"
  // margin: '50px'
};

const proportion = (outMin, outMax) => (value, inMin, inMax) => {
  const limitedValue = Math.max(Math.min(value, inMax), inMin);
  const inputRatio = (limitedValue - inMin) / (inMax - inMin);
  return outMin + inputRatio * (outMax - outMin);
};

const relativise = (eventLocation, targetBox, max) => {
  const scale = proportion(-max, max);

  return {
    x: scale(eventLocation.x, targetBox.left, targetBox.right),
    y: scale(eventLocation.y, targetBox.top, targetBox.bottom)
  };
};

export default class MousePad extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      interval: null,
      position: {
        x: 0,
        y: 0
      }
    };
  }

  componentDidMount() {
    const tickFunction = () => {
      if (this.state.position.x !== 0 && this.state.position.y !== 0)
        this.props.moveMouse(this.state.position);
    };

    this.setState({
      interval: setInterval(tickFunction.bind(this), this.props.tickMillis)
    });
  }

  componentWillUnmount() {
    clearInterval(this.state.interval);
  }

  mouseEvent(event) {
    const absPosition = {
      x: event.clientX,
      y: event.clientY
    };
    const elPosition = event.target.getBoundingClientRect();

    this.moveMouse(absPosition, elPosition);
  }

  touchEvent(event) {
    const absPosition = {
      x: event.touches.item(0).clientX,
      y: event.touches.item(0).clientY
    };
    const elPosition = event.target.getBoundingClientRect();

    this.moveMouse(absPosition, elPosition);
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
        style={style}
        onMouseMove={this.mouseEvent.bind(this)}
        onMouseLeave={this.resetPosition.bind(this)}
        onTouchMove={this.touchEvent.bind(this)}
        onTouchEnd={this.resetPosition.bind(this)}
      />
    );
  }
}
