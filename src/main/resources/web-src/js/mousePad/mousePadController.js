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
