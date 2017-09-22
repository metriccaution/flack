import React from 'react'
import ReactDOM from 'react-dom'
import { connect, Provider } from 'react-redux'
import createStore from './redux'
import wsSub from './ws/reduxSubscriber'

import { move } from './mouse/actions'

const store = createStore()
wsSub()(store)

store.dispatch(move({x : 5, y : 5}))
store.dispatch(move({x : 10, y : 10}))
setTimeout(() => store.dispatch(move({x : 0, y : 0})), 1000)

const El = () => <div>
  Hello from React - Now built with maven
</div>

ReactDOM.render(<Provider store = { store }>
  <El />
</Provider>, document.getElementById('root'))
