import ws from './main'

/*
 * Limit how often a mapper can produce actions
 */
const limitRate = (maxRate) => (fcn) => {
  let lastCall = 0
  return (current, prev) => {
    const now = new Date().getTime()
    if (now < lastCall + maxRate)
      return []

    lastCall = now
    return fcn(current, prev)
  }
}

/*
 * A preconfigured version of limitRate
 */
const limitTick = limitRate(10)

/*
 * Get the movement of the mouse
 */
const updateMouse = (current) => [{
  type: 'mouse.move',
  x: current.mouse.position.x,
  y: current.mouse.position.y
}]

/*
 * Turn a change in state into websocket actions
 */
const storeToActions = () => {
  const mappers = [
    limitTick(updateMouse)
  ]

  return (current, prev) => mappers
      .map((mapFcn) => mapFcn(current, prev))
      .reduce((joined, actions) => joined.concat(actions), [])
}


/*
 * Listens to the Redux store, populated by the UI controls, and translates
 * that into websocket messages
 */
export default () => {
  let prevState = undefined

  return (store) => {
    const wsConfig = store.getState().websocket
    const send = (a) => ws(wsConfig)
    const stateMapper = storeToActions()

    store.subscribe(() => {
      const currentState = store.getState()
      stateMapper(currentState, prevState).forEach(send)
      prevState = currentState
    })
  }
}
