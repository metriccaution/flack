const ws = new window.WebSocket('ws://localhost:9731/control')
ws.onopen = () => console.log('Opened')
ws.onmessage = () => console.log('Mess')

setTimeout(() => ws.send(JSON.stringify({ type: 'mouse.move', x : 50, y : 50 })), 1000)
setTimeout(() => ws.send(JSON.stringify({ type: 'mouse.click', down : true, code : 1 })), 2000)
setTimeout(() => ws.send(JSON.stringify({ type: 'mouse.scroll', distance : 1 })), 3000)
