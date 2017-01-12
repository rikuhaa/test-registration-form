/*jshint node: true */
'use strict';

var gulp = require('gulp');
var browserify = require('browserify');
// required to convert browserify result stream
// to gulp vinyl stream
var source = require('vinyl-source-stream');

var distDir = 'dist';
if (process.env.DIST_DIR) {
  distDir = process.env.DIST_DIR;
}

gulp.task('build-js', function() {
  var browserifyIns = browserify({
    'entries': './js/app.js',
    'paths': ['./js'],
    'debug': false
  });
  return browserifyIns.bundle()
  	.pipe(source('bundle.js'))
    .pipe(gulp.dest(distDir));
});

gulp.task('copy-css-dep', function() {
  gulp.src('./node_modules/angular-material/angular-material.css')
    .pipe(gulp.dest(distDir));
});

gulp.task('dist', ['build-js', 'copy-css-dep'], function() { 
  return gulp.src('index.html')
    .pipe(gulp.dest(distDir));
});