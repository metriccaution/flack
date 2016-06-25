var service = require('./mouseClickService');

var addListeners = function(element, buttons) {
  var clickDown = function() {
    service(buttons, true);
  };

  var clickUp = function() {
    service(buttons, false);
  };

  element.addEventListener('touchstart', function(e){
    clickDown();
  });

  element.addEventListener('touchmove', function(e){
    clickDown();
  });

  element.addEventListener('touchend', function(e){
    clickUp();
  });

  element.addEventListener('touchenter', function(e){
    clickDown();
  });

  element.addEventListener('touchleave', function(e){
    clickUp();
  });

  element.addEventListener('touchcancel', function(e){
    clickUp();
  });
};

var setup = function(selector, buttons) {
  var elements = document.querySelectorAll(selector);
  if (elements.length !== 1) {
    throw new Error(selector + ' refers to ' + elements.length + ' elements');
  }

  var element = elements[0];
  addListeners(element, buttons);
};

module.exports = setup;
