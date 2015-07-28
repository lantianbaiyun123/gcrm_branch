module.exports = function (grunt) {
    grunt.loadNpmTasks('grunt-html2js');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.initConfig({
        "clean": {
          'build': ['build']
        },
        "html2js": {
            options: {
              base: './src',
              module: 'app-templates'
            },
            main: {
              // src: ['./src/app/**/*.tpl.html'],
              src: ['./src/app/_common/**/*.tpl.html'],
              dest: './src/app/app-templates.js'
            }
        },
        "jshint": {
          src: [
            './src/app/**/*.js'
          ],
          options: {
            // bitwise: false,
            expr: true, //允许短路 a && b();
            curly: true,
            immed: true,   //
            noarg: true,   //禁止使用arguments.caller and arguments.callee
            sub: true, //允许使用 $scope['name'] ,而不仅仅$scope.name
            eqnull: true, //允许使用 == null
            // eqeqeq: true,
            trailing: true
          },
          globals: {}
        },
        "watch": {
          options: {
            livereload: true
          },
          files: [
            // './src/app/**/*.tpl.html',
            './src/app/**/*.js'
          ],
          // tasks: [ 'clean', 'html2js', 'copy', 'jshint']
          tasks: ['jshint']
        },
        "requirejs": {
          'build': {
            options: {
              // name: 'main',
              // baseUrl: "./resources/js/app",
              mainConfigFile: "./src/app/main-min.js",
              // out: "bin/px-min.js"
              // appDir: 'resources/js/app',
              dir: 'build',
              // optimize: 'none'
            }
          }
        },
        "copy": {
          'build': {
            files: [
              {
                src: [ '**' ],
                dest: '../resources/app',
                cwd: './build',
                expand: true
              },
              {
                src: [ 'vendor/**' ],
                dest: '../resources/'
              },
              {
                src: [ 'src/images/**' ],
                dest: '../resources/images/',
                expand: true,
                flatten: true,
                filter: 'isFile'
              },
              {
                src: [ '**/*.tpl.html', '**/*.json'],
                cwd: 'src/app',
                dest: '../resources/app/',
                expand: true,
                filter: 'isFile'
              }
           ]
          }
        },
        "concat": {
          'build': {
            files: [
              // {
              //   src: [ 'resources/lib/requirejs/require.js', 'build/resources/js/app/main.js'],
              //   dest: 'build/resources/js/app/main.js'
              // },
              {
                src: [ 'vendor/angular-loading-bar/loading-bar.css', 'vendor/angular-ui-tree/angular-ui-tree.min.css' ,'src/app/**/*.css' ],
                dest: 'build/app.css'
              },
              {
                src: [  'vendor/requirejs/require.js', 'build/main.js', 'build/home/CtrlHome.js' ],
                dest: 'build/main.js'
              }
            ]
          }
        },
        "uglify": {
          'compile': {
            options: {
              report: 'gzip'
            },
            files: [
              {
                expand: true,
                cwd: '../resources',
                src: 'app/**/*.js',
                dest: '../../resources/hk/js'
              },
              {
                expand: true,
                cwd: '.',
                src: 'vendor/**/*.js',
                dest: '../../resources/hk/js'
              }
            ]
          },
          // 'requirejs': {
          //   files: {
          //     '../../resources/hk/js/vendor/requirejs/require.js': ['../resources/vendor/requirejs/require.js']
          //   }
          // },
          // 'build': {
          //   options: {
          //     // compress: false
          //     // beautify: true
          //     preserveComments: false
          //   },
          //   files: { '../resources/app/main.js': '../resources/app/main.js'}
          // }
        },
        "cssmin": {
          'compile': {
            files: {
              '../resources/app/app.css': ['../resources/app/app.css'],
              '../resources/vendor/bootstrap/css/bootstrap.css': ['../resources/vendor/bootstrap/css/bootstrap.css'],
              '../resources/vendor/select2-3.4.5/select2.css': ['../resources/vendor/select2-3.4.5/select2.css']
            },
            options: {
              report: 'gzip'
            }
          }
        }
    });
    grunt.registerTask('replaceMainJs', 'replaceMainJs vendor path', function () {
      grunt.file.copy('build/main.js', 'build/main.js', {
        process: function ( contents, path ) {
          return contents.replace(/\.\.\/\.\.\/vendor/g, '../vendor');
        }
      });
    });
    grunt.registerTask('replaceAppTemplates', 'replaceAppTemplates', function () {
      grunt.file.copy('src/app/app-templates.js', 'src/app/app-templates.js', {
        process: function ( contents, path ) {
          return contents.replace(/app\/_common/g, '../resources/app/_common');
        }
      });
    });
    grunt.registerTask('updateTimestamp', 'replace js', function () {
      var timestamp = getTimestamp();
      grunt.file.copy('src/main.jsp', '../views/main.jsp', {
        process: function ( contents, path ) {
          return contents.replace(/v=\d{9,11}/g, 'v='+ timestamp);
        }
      });
      grunt.file.copy('src/app/main.js', 'src/app/main.js', {
        process: function ( contents, path ) {
          return contents.replace(/v=\d{9,11}/g, 'v='+ timestamp);
        }
      });
    });
    grunt.registerTask('default', ['watch']);
    grunt.registerTask('build', ['html2js', 'clean:build', 'replaceAppTemplates', 'updateTimestamp', 'requirejs:build', 'replaceMainJs', 'concat:build', 'copy:build']);
    grunt.registerTask('compress', ['cssmin:compile', 'uglify:compile']);
    grunt.registerTask('compile', ['build', 'compress']);

    function getTimestamp () {
      var timestamp,
          today = new Date(),
          year = today.getFullYear(),
          month = today.getMonth() + 1,
          day = today.getDate();
          hh = today.getHours();
          mm = today.getMinutes();

          month = month < 10 ? '0' + month : month;
          day = day < 10 ? '0' + day : day;
          hh = hh < 10 ? '0' + hh : hh;
          mm = mm < 10 ? '0' + mm : mm;

      // var num = Math.floor(Math.random() * 1000);
      // var stamp = year + month + day + num;
      var stamp = year + month + day + hh;
      return stamp;
    }
}