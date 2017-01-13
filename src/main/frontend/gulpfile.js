/*jshint node: true */
'use strict';

// TODO js / css minification
// SASS integration
// angular template caching (though no actual templates yet)

var gulp = require('gulp');
var browserify = require('browserify');
// required to convert browserify result stream
// to gulp vinyl stream
var source = require('vinyl-source-stream');
// static code analysis tools
var jshint = require('gulp-jshint');
var jscs = require('gulp-jscs');
// tool for renaming files that need cache busting
var CacheBuster = require('gulp-cachebust');
// karma for running tests
var karma = require('karma');
// this is needed for changing karma test failures 
// to nicer looking errors
var gutil = require('gulp-util');
// for quick development deployment of the client side
var webserver = require('gulp-webserver');

var distDir = 'dist';
if (process.env.DIST_DIR) {
  distDir = process.env.DIST_DIR;
}

/*
* This task starts a karma server using the 'tdd' config.
*
* This configuration will run all the spec files in the
* test-directory and re-run all the tests when a test 
* or source js-file changes.
*/
gulp.task('tdd', function(done) {
  new karma.Server({
    configFile: __dirname + '/karma_tdd_conf.js',
    singleRun: false
  }, done).start();
});

gulp.task('test', function(done) {
  new karma.Server({
    configFile: __dirname + '/karma_oneshot_conf.js',
    singleRun: true
  }, function(err){
    // if the karma error status != 0, use gulp-util
    // to fail the build relatively cleanly
    if(err === 0){
      done();
    } else {
      done(new gutil.PluginError('karma', {
        message: 'Karma Tests failed'
      }));
    }
  }).start();
});

// this instance is needed for storing the cachebusted
// names of eg. bundle.js so that they can be rewrited
// in index.html in final stage
var cachebusterInst = new CacheBuster();

/*
* Run the jshint js code linter with default settings
* on all the js code
*/
gulp.task('jshint', function() {
  return gulp.src('./js/**/*.js')
    .pipe(jshint())
    .pipe(jshint.reporter('default'))
    .pipe(jshint.reporter('fail'));
});

/*
* Run the jscs js style checker on all the js code.
* Uses settings from '.jscsrc'
*/
gulp.task('jscs', function() {
  return gulp.src('./js/**/*.js')
    .pipe(jscs())
    .pipe(jscs.reporter())
    .pipe(jscs.reporter('fail'));
});

/*
* Task that uses browserify to bundle the 
* js files to one bundle.js and resolves all 
* the dependencies
*/
gulp.task('build-js', [
    'test', 'jscs', 'jshint'
  ], function() {
    var browserifyIns = browserify({
      'entries': './js/app.js',
      'paths': ['./js'],
      'debug': false
    });
    return browserifyIns.bundle()
    	.pipe(source('bundle.js'))
      .pipe(cachebusterInst.resources())
      .pipe(gulp.dest(distDir));
  });

/*
* Task that copies contents of img-folder to 
* target folder
*/
gulp.task('copy-img', function() {
  return gulp.src('./img/*')
    .pipe(gulp.dest(distDir + '/img'));
});

/*
* Task for copying required css files to the 
* target folder
*/
gulp.task('copy-css', function() {
  gulp.src('./node_modules/angular-material/angular-material.css')
    .pipe(cachebusterInst.resources())
    .pipe(gulp.dest(distDir + '/css'));
  gulp.src('./css/app.css')
    .pipe(cachebusterInst.resources())
    .pipe(gulp.dest(distDir + '/css'));
});

/*
* Task that waits for other build tasks to complete
* and at the end updates the cachebusted resource names
* to the index file and copies the resulting index.html
* to the target directory
*/
gulp.task('dist', ['build-js', 'copy-css', 'copy-img'], function() { 
  return gulp.src('index.html')
    .pipe(cachebusterInst.references())
    .pipe(gulp.dest(distDir));
});

// As the project grows these tasks should be refactored
// to use watchify in order to be able to do the build 
// incrementally (and not for the whole browserify 
// require change)

/*
* Task that watches for changes in the source files and
* triggers a new 'dist' build on changes
*/
gulp.task('watch', function() {
  return gulp.watch(['./index.html', './css/app.css',
    './js/**/*/js'], ['dist']);
});

/*
* A task that sets up a lightweigth webserver for testing 
* the frontend implementation (without necessarily having 
* backend services running) and watches for changes in 
* the source files
*/
gulp.task('develop', ['dist', 'watch'], function() {
  gulp.src(distDir)
    .pipe(webserver({
      'livereload': false,
      'host': 'localhost',
      'open': 'http://localhost:8000/index.html'
    }));
});