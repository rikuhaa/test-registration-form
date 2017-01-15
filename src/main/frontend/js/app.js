/*jshint browserify: true */
'use strict';

var angular = require('angular');
require('angular-material');
require('angular-messages');

angular.module('k15t-pat-registration', [
  'ngMaterial', 'ngMessages'
]);

require('./registrationService.js');
require('./registrationController.js');

