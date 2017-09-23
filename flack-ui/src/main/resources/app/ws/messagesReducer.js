const defaultState = {
  pending : []
}

export const sendMessages = (ids) => ({
  type: 'sendMessages',
  payload: ids
})

const idGen = () => Math.floor(Math.random() * 100000)

/*
 * Stores the state for any messages that are currently in flight
 */
export default (state = defaultState, action = {}) => {
  switch (action.type) {
    // Mark some list of messages as sent
    case 'sendMessages':
      const filteredMessages = state.pending.filter((msg) => action.payload.indexOf(msg.id) === -1)
      return Object.assign({}, state, { pending: filteredMessages })
      break

    case 'mouse.move':
      const newMessage = {
        id: idGen(),
        body: action
      }
      return Object.assign({}, state, { pending : state.pending.slice().concat(newMessage) })
      break
  }
  return state
}
