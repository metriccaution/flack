const defaultState = {
  pending : []
}

export const sendMessages = (ids) => ({
  type: 'sendMessages',
  payload: ids
})

let count = 0;
const idGen = () => `${count++}`;

/*
 * Stores the state for any messages that are currently in flight
 */
export default (state = defaultState, action = {}) => {
  switch (action.type) {
    // Mark some list of messages as sent
    case 'sendMessages':
      const filteredMessages = state.pending.filter((msg) => action.payload.indexOf(msg.id) === -1)
      return Object.assign({}, state, { pending: filteredMessages })

    case 'mouse.move':
      const newMessage = {
        id: idGen(),
        body: action
      }
      return Object.assign({}, state, { pending : state.pending.slice().concat(newMessage) })

    default:
      return state
  }
}
