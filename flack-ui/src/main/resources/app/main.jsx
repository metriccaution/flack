import React from 'react'
import ReactDOM from 'react-dom'
import { connect, Provider } from 'react-redux'
import createStore from './redux'
import wsSub from './ws/reduxSubscriber'
import MousePad from './mouse/MousePad.jsx'
import { move } from './mouse/actions'

const store = createStore()
wsSub()(store)

store.dispatch(move({x : 5, y : 5}))
store.dispatch(move({x : 10, y : 10}))
setTimeout(() => store.dispatch(move({x : 0, y : 0})), 1000)

const App = (props) => <div>
  <MousePad moveMouse = { props.moveMouse } tickMillis = { 10 } maxPerTick = { 10 } />
</div>

const Joined = connect(state => ({
  mousePosition: state.mouse.position
}), dispatch => ({
  moveMouse: (position) => dispatch(move(position))
}))(App)

ReactDOM.render(<Provider store = { store }>
  <Joined />
</Provider>, document.getElementById('root'))
