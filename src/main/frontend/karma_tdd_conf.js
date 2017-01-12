/*jshint node: true */
'use strict';

module.exports = function(config) {
  config.set({
    basePath: '',
    logLevel: config.LOG_INFO,
    frameworks: ['browserify', 'jasmine'],
    reporters: ['spec'],
    files: [
      'js/**/*.js',
      'node_modules/angular-mocks/angular-mocks.js',
      'test/**/*.js'
    ],
    autoWatch: true,
    singleRun: false,
    browsers: ['PhantomJS'],
    failOnEmptyTestSuite: false,
    preprocessors: {
      'js/**/*.js': ['browserify']
    },
    browserify: {
      debug: true
    }
  });
};
