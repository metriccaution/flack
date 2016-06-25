var ws = require('../remote/configuredRemote');

var config = {
  type : 'MouseClick'
};

var click = function(buttons, down) {
  ws({
    type : config.type,
    buttons : buttons,
    down : down
  });
};

module.exports = click;
