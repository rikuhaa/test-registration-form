module.exports = function(config) {
  config.set({
    basePath: '',
    logLevel: config.LOG_INFO,
    frameworks: ['browserify', 'jasmine'],
    reporters: ['spec', 'coverage'],
    files: [
      'js/**/*.js',
      'node_modules/angular-mocks/angular-mocks.js',
      'test/**/*.js'
    ],
    autoWatch: false,
    singleRun: true,
    browsers: ['PhantomJS'],
    preprocessors: {
      'js/**/*.js': ['browserify']
    },
    browserify: {
      debug: true,
      "transform": ["browserify-istanbul"]
    },
    coverageReporter: {
      type: 'html',
      dir: 'gen/',
      check: {
        global: {
          statements: 80,
          lines: 80,
          functions: 80,
          branches: 80
        }
      }
    }
  });
};
