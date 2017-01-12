/*jshint node: true */
'use strict';

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

var distDir = 'dist';
if (process.env.DIST_DIR) {
  distDir = process.env.DIST_DIR;
}

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
gulp.task('build-js', ['jscs', 'jshint'], function() {
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