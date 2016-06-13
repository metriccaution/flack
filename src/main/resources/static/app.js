(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
window.onload = function() {
  var ms = require('./mousePad/mousePadController');
  ms('#test');
};

},{"./mousePad/mousePadController":2}],2:[function(require,module,exports){
var service = require('./mousePadService');

var addListeners = function(element) {

  var moveLocation = function(e) {
      var touch = e.changedTouches[0];

      var location = {
        x   : parseInt(touch.clientX),
        y   : parseInt(touch.clientY),
        elX : element.offsetWidth,
        elY : element.offsetHeight
      };

      service.location(location);
  };

  element.addEventListener('touchstart', function(e){
    moveLocation(e);
    service.start();
  });

  element.addEventListener('touchmove', function(e){
    moveLocation(e);
  });

  element.addEventListener('touchend', function(e){
    service.stop();
  });

  element.addEventListener('touchenter', function(e){
    moveLocation(e);
  });

  element.addEventListener('touchleave', function(e){
    service.stop();
  });

  element.addEventListener('touchcancel', function(e){
    service.stop();
  });
};

var setup = function(selector) {
  var elements = document.querySelectorAll(selector);
  if (elements.length !== 1) {
    throw new Error(selector + ' refers to ' + elements.length + ' elements');
  }

  var element = elements[0];
  addListeners(element);
};

module.exports = setup;

},{"./mousePadService":3}],3:[function(require,module,exports){
var ws = require('../remote/configuredRemote');

var config = {
  maxX : 20,
  maxY : 20,
  interval : 10
};

var location = {
  x : 0,
  y : 0,
  type : 'MouseMove'
};

var ticker;

var updateLocation = function(newLocation) {
  var normalisedX = config.maxX * (newLocation.x - newLocation.elX / 2) / (newLocation.elX / 2);
  var normalisedY = config.maxY * (newLocation.y - newLocation.elY / 2) / (newLocation.elY / 2);

  location.x = normalisedX,
  location.y = normalisedY;
};

var startDown = function() {
  if (!ticker) {
    ticker = setInterval(function() {
      ws(location);
    }, config.interval);
  }
};

var stopDown = function() {
  if (ticker) {
    clearInterval(ticker);
    ticker = undefined;
  }
};

module.exports = {
  location  : updateLocation,
  start     : startDown,
  stop      : stopDown
};

},{"../remote/configuredRemote":4}],4:[function(require,module,exports){
var host = window.location.host;
var path = '/control';

module.exports = require('./remote')({
  location : 'ws://' + host + path
});

},{"./remote":5}],5:[function(require,module,exports){
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

},{}]},{},[1]);
