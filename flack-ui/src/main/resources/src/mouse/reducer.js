const defaultState = {
  position: {
    x : 0,
    y : 0
  }
}

export default (state = defaultState, action) => {
  switch (action.type) {
    case 'mouse.move':
      return Object.assign({}, state, { position: { x : action.x, y : action.y } })

    default:
      return state
  }
}
