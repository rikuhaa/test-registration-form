/*jshint browserify: true */
'use strict';

var angular = require('angular');

angular.module('k15t-pat-registration').controller(
  'registrationController', ['$scope', function($scope) {

    $scope.registrationComplete = false;

  }]);
