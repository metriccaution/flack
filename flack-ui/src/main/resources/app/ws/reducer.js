import { combineReducers } from 'redux'
import config from './configReducer'
import messages from './messagesReducer'

export default combineReducers({
  config,
  messages
})
