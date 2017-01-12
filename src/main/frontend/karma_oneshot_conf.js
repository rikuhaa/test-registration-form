module.exports = function(config) {
  config.set({
    basePath: '',
    logLevel: config.LOG_INFO,
    frameworks: ['browserify', 'jasmine'],
    reporters: ['spec'],
    files: [
      'js/**/*.js',
      'test/**/*.js'
    ],
    autoWatch: false,
    singleRun: true,
    browsers: ['PhantomJS'],
    preprocessors: {
      'js/**/*.js': ['browserify']
    },
    browserify: {
      debug: true
    }
  });
};
