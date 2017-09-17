import ws from './main'

/*
 * Listens to the Redux store, populated by the UI controls, and translates
 * that into websocket messages
 */
export default (store) => {
  const state = store.getState()
  const wsConfig = state.websocket

  const connection = ws(wsConfig)

  let mousePosition = state.mouse.position

  // Send mouse events at a fixed interval
  setInterval(() => {
    if (!mousePosition.x && !mousePosition.y)
      return

    connection({
      type: 'mouse.move',
      x: mousePosition.x,
      y: mousePosition.y
    })
  }, 10)

  store.subscribe(() => {
    mousePosition = store.getState().mouse.position
    console.log(mousePosition)
  })
}
