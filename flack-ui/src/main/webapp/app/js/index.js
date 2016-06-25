window.onload = function() {
  var ms = require('./mousePad/mousePadController');
  ms('#scroll-pad');

  var mc = require('./mouseClick/mouseClickController');
  mc('#btn-one', [2]);
  mc('#btn-two', [1]);
};
