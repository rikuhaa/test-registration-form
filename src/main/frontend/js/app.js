/*jshint browserify: true */
'use strict';

var angular = require('angular');
require('angular-material');

angular.module('k15t-pat-registration', [
  'ngMaterial'
]);

require('./registrationController.js');
