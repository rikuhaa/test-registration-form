/*jshint node: true */
'use strict';

var gulp = require('gulp');

gulp.task('dist', function() {
  var distDir = 'dist';
  if (process.env.DIST_DIR) {
    distDir = process.env.DIST_DIR;
  }
  return gulp.src('index.html')
    .pipe(gulp.dest(distDir));
});