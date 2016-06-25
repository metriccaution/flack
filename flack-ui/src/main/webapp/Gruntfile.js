module.exports = function(grunt) {

  grunt.initConfig({
      browserify : {
        client: {
          src: ['app/js/index.js'],
          dest: 'dist/app.js'
        }
      },

      copy: {
        html: {
          expand:true,
          cwd:'app/html',
          src: '*',
          dest: 'dist/'
        },

        css : {
          expand:true,
          cwd:'app/styles',
          src: '*',
          dest: 'dist/'
        }
      }
  });

  grunt.loadNpmTasks('grunt-browserify');
  grunt.loadNpmTasks('grunt-contrib-copy');

  grunt.registerTask('default', ['browserify', 'copy'])
};
