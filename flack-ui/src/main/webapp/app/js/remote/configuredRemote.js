var host = window.location.host;
var path = '/control';

module.exports = require('./remote')({
  location : 'ws://' + host + path
});
