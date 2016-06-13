var ws = require('../remote/configuredRemote');

var config = {
  maxX : 20,
  maxY : 20,
  interval : 10
};

var location = {
  x : 0,
  y : 0
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
