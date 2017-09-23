import React from 'react'

const style = {
  height: '200px',
  width: '200px',
  backgroundColor: 'chartreuse',
  margin: '50px'
}

const lock = (min, max) => (value) => Math.min(Math.max(value, min), max)
const proportion = lock(-1, 1)

const relativise = (eventLocation, targetBox) => {
  const width = (targetBox.right - targetBox.left)
  const height = (targetBox.bottom - targetBox.top)

  return {
    x : proportion((eventLocation.x - width / 2) / width),
    y : proportion((eventLocation.y - height / 2) / height)
  }
}

export default class MousePad extends React.Component {

  constructor(props) {
    super(props)

    this.state = {
      interval : null,
      position : {
        x : 0,
        y : 0
      }
    }
  }

  componentDidMount() {
    const tickFunction = () => {
      if (this.state.position.x !== 0 && this.state.position.y !== 0)
        this.props.moveMouse(this.state.position)
    }

    const interval = setInterval(tickFunction.bind(this), this.props.tickMillis)
  }

  componentWillUnmount() {
    clearInterval(this.state.interval)
  }

  mouseEvent(event) {
    const absPosition = {
      x : event.clientX,
      y : event.clientY
    }
    const elPosition = event.target.getBoundingClientRect()

    this.moveMouse(absPosition, elPosition)
  }

  touchEvent(event) {
    const absPosition = {
      x : event.touches.item(0).clientX,
      x : event.touches.item(0).clientY
    }
    const elPosition = event.target.getBoundingClientRect()

    this.moveMouse(absPosition, elPosition)
  }

  resetPosition() {
    console.log('Ending')
    this.moveMouse({x : 0, y : 0})
  }

  moveMouse(position, containerPosition = {top : 0, bottom : 1, left : 0, right : 1}) {
    const relativePosition = relativise(position, containerPosition)
    this.setState({
      position: {
        x: Math.floor(relativePosition.x * this.props.maxPerTick),
        y: Math.floor(relativePosition.y * this.props.maxPerTick)
      }
    })
  }

  render() {
    return <div
        style         = { style }
        onMouseMove   = { this.mouseEvent.bind(this) }
        onMouseLeave  = { this.resetPosition.bind(this) }
        onTouchMove   = { this.touchEvent.bind(this) }
        onTouchEnd    = { this.resetPosition.bind(this) } />
  }

}
