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


var distDir = 'dist';
if (process.env.DIST_DIR) {
  distDir = process.env.DIST_DIR;
}

gulp.task('jshint', function() {
  return gulp.src('./js/**/*.js')
    .pipe(jshint())
    .pipe(jshint.reporter());
});

gulp.task('jscs', function() {
  return gulp.src('./js/**/*.js')
    .pipe(jscs())
    .pipe(jscs.reporter());
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
    .pipe(gulp.dest(distDir + '/css'));
  gulp.src('./css/app.css')
    .pipe(gulp.dest(distDir + '/css'));
});

gulp.task('dist', ['build-js', 'copy-css', 'copy-img'], function() { 
  return gulp.src('index.html')
    .pipe(gulp.dest(distDir));
});