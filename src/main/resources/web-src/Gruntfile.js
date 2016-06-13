module.exports = function(grunt) {

  grunt.initConfig({
      browserify : {
        client: {
          src: ['app/js/index.js'],
          dest: 'dist/app.js'
        }
      },

      copy: {
        main: {
          expand:true,
          cwd:'app/html',
          src: '*',
          dest: 'dist/',
        }
      }
  });

  grunt.loadNpmTasks('grunt-browserify');
  grunt.loadNpmTasks('grunt-contrib-copy');

  grunt.registerTask('default', ['browserify', 'copy'])
};
