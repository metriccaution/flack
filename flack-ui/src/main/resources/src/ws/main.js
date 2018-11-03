/*
 * Handles making and maintaining a websocket connection
 */

const ws = ({ url }) => {
  const connection = new WebSocket(url);

  let ready = false;
  const pending = [];

  // Send data as JSON
  const sendObj = data => connection.send(JSON.stringify(data));

  // When the connection opens, clear down the backlog
  connection.onopen = () => {
    ready = true;
    pending.forEach(sendObj);
  };

  return data => {
    // If we can send now, do
    if (ready) return sendObj(data);
    // Otherwise, put it on a queue of messages to send
    pending.push(data);
  };
};

export default ws;
