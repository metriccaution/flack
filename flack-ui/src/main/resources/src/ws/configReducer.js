import Url from 'url-parse'

const base = new Url(document.location.href, false)

const defaultState = {
  url: 'ws://' + base.host + '/control'
}

/*
 * Stores the current config for the websocket - Currently static
 */
export default (state = defaultState, action) => {
  return state
}
