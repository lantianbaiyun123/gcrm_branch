var
  gulp = require('gulp'),
  jshint = require('gulp-jshint'),
  stylish = require('jshint-stylish'),
  concat = require('gulp-concat'),
  html2js = require('gulp-html2js'),
  rjs = require('requirejs'),
  replace = require('gulp-replace'),
  uglify = require('gulp-uglify'),
  minifyCss = require('gulp-minify-css'),
  es = require('event-stream'),
  bird = require('gulp-bird'),
  birdConfig = require('./bird-config.json');

var BUILD_DIR = '../resources';
var RELEASE_DIR = '../../resources/hk/js';
var RELEASE_DIR_2 = '../../resources/hk02/js';
gulp.task('b', ['rjs', 'build-copy-vendor', 'build-copy-data', 'build-copy-tpl', 'build-copy-images', 'build-copy-index', 'build-copy-json', 'build-merge-css', 'build-concat', 'updateTimestamp']);
gulp.task('build', ['rjs', 'build-copy-vendor', 'build-copy-data', 'build-copy-tpl', 'build-copy-images', 'build-copy-index', 'build-copy-json', 'build-merge-css', 'build-concat', 'updateTimestamp']);
gulp.task('r', ['release-cssmin', 'release-jsmin']);
gulp.task('release', ['release-cssmin', 'release-jsmin']);


/**
 * hint for all src javascript
 */
gulp.task('hint', function () {
  return gulp.src('src/app/**/*.js')
    .pipe(jshint({
      es3: true
    }))
    .pipe(jshint.reporter(stylish));
});
/**
 * template merge for common templates
 */
gulp.task('html2js', function () {
  return gulp.src('./src/app/_common/**/*.tpl.html')
    .pipe(html2js({
      outputModuleName: 'app-templates',
      base: './src'
    }))
    .pipe(concat('app-templates.js'))
    .pipe(gulp.dest('src/app'));
});
/**
 * r.js build
 */
gulp.task('rjs', ['html2js'], function (cb) {
  rjs.optimize({
    mainConfigFile: "./src/app/main-min.js",
    dir: BUILD_DIR + '/app'
  }, function () {
    cb();
    console.log ('r.js done\n');
  });
});
/**
 * copy work
 * @description copy vendor,data,tpl,json,images and index to build dir
 */
gulp.task('build-copy-vendor', function () {
  return gulp.src(['vendor/**'])
    .pipe(gulp.dest(BUILD_DIR + '/vendor'))
});
gulp.task('build-copy-data', function () {
  return gulp.src(['data/*.json', 'data/*.js', 'data/*.djson'])
    .pipe(gulp.dest(BUILD_DIR + '/data'))
});
gulp.task('build-copy-tpl', function () {
  return gulp.src(['src/app/**/*.tpl.html'])
    .pipe(gulp.dest(BUILD_DIR + '/app'))
});
gulp.task('build-copy-json', function () {
  return gulp.src(['src/app/**/*.json'])
    .pipe(gulp.dest(BUILD_DIR + '/app'))
});
gulp.task('build-copy-images', function () {
  return gulp.src(['src/images/*.png', 'src/images/*.gif', 'src/images/*.jpg'])
    .pipe(gulp.dest(BUILD_DIR + '/images'))
});
gulp.task('build-copy-index', function () {
  return gulp.src(['src/index.html'])
    .pipe(gulp.dest(BUILD_DIR + ''))
});
/**
 * some replacement work
 */
var timestamp = getTimestamp();
gulp.task('updateTimestamp', ['build-concat'],function(){
  var mainjsp = gulp.src(['src/main.jsp'])
    .pipe(replace(/\{\{timeStamp\}\}/g, timestamp))
    .pipe(gulp.dest('../views'));
  var mainjs = gulp.src([BUILD_DIR + '/app/main.js'])
    .pipe(replace(/\{\{timeStamp\}\}/g, timestamp))
    .pipe(gulp.dest(BUILD_DIR + '/app'));
  return es.concat(mainjsp, mainjs);
});

// gulp.task('replaceMainJs', ['rjs'], function(){
//   return gulp.src([BUILD_DIR + '/app/main.js'])
//     .pipe(replace(/\.\.\/\.\.\/vendor/g, '../vendor'))
//     .pipe(gulp.dest(BUILD_DIR + '/app'));
// });

gulp.task('build-concat', ['rjs'], function () {
  return gulp.src(['vendor/requirejs/require.js', BUILD_DIR + '/app/main.js', BUILD_DIR + '/app/home/CtrlHome.js'])
    .pipe(concat('main.js'))
    .pipe(gulp.dest(BUILD_DIR + '/app'));
})
/**
 * merge css
 * @return {[type]} [description]
 */
gulp.task('build-merge-css', ['rjs'], function () {
  return gulp.src(['vendor/angular-loading-bar/loading-bar.css', 'vendor/angular-ui-tree/angular-ui-tree.min.css' ,'src/app/**/*.css'])
    .pipe(concat('app.css'))
    .pipe(gulp.dest(BUILD_DIR + '/app'));
})
/**
 * uglify-css
 */
gulp.task('release-cssmin', function () {
  var minApp = gulp.src(['../resources/app/app.css'])
    .pipe(minifyCss())
    .pipe(gulp.dest('../resources/app'));
  var minBoot = gulp.src(['../resources/vendor/bootstrap/css/bootstrap.css'])
    .pipe(minifyCss())
    .pipe(gulp.dest('../resources/vendor/bootstrap/css'));
  var minSelect2 = gulp.src(['../resources/vendor/select2-3.4.5/select2.css'])
    .pipe(minifyCss())
    .pipe(gulp.dest('../resources/vendor/select2-3.4.5'));
  return es.concat(minApp, minBoot, minSelect2);
});
/**
 * uglify-js
 */
gulp.task('release-jsmin', function () {
  return gulp.src([BUILD_DIR + '/**/*.js'])
    .pipe(uglify())
    .pipe(gulp.dest(RELEASE_DIR))
    .pipe(gulp.dest(RELEASE_DIR_2));
});


gulp.task('default', function () {
  bird.start(birdConfig.servers, birdConfig.transRules);
  gulp.watch(['src/app/**/*', "!src/app/app-templates.js"], ['hint', 'b']);
});

gulp.task('start', function() {
  bird.start(birdConfig.servers, birdConfig.transRules);
  gulp.watch(['src/app/**/*', "!src/app/app-templates.js"], ['hint', 'b']);
});

function getTimestamp() {
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
  var stamp = '' + year + month + day + hh;
  return stamp;
}
