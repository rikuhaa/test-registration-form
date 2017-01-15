/*jshint browserify: true */
'use strict';

var angular = require('angular');

angular.module('k15t-pat-registration').controller(
  'registrationController', [
    '$scope', 'registrationService',
    '$mdDialog', '$translate', function(
      $scope, registrationService,
      $mdDialog, $translate) {

    $scope.registrationComplete = false;

    $scope.user = {};

    $scope.changeLanguage = function(lan) {
      $translate.use(lan);
    };

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
