var connect = function(config) {
  var openChecker;
  var socket = new WebSocket(config.location);

  var isOpen = function() {
    return socket.readyState === 1;
  };

  var send = function(message) {
    // TODO - Queue until ready
    socket.send(JSON.stringify(message));
  };

  return send;
};

module.exports = connect;
