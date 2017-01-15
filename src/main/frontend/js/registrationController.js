/*jshint browserify: true */
'use strict';

var angular = require('angular');

angular.module('k15t-pat-registration').controller(
  'registrationController', [
    '$scope', 'registrationService',
    '$mdDialog', function(
      $scope, registrationService, $mdDialog) {

    $scope.registrationComplete = false;

    $scope.user = {};

    $scope.registerUser = function() {
      registrationService
        .registerNew('test')
        .then(function(res) {
          if (res.success) {
            $mdDialog.show(
              $mdDialog.alert()
                .title('registered!'));
          } else {
            $mdDialog.show(
              $mdDialog.alert()
                .title('Error in regstration!'));
          }
        });
    };

  }]);
