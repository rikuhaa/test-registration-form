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
    // TODO could attach some semi-transparent curtain to this
    $scope.processing = false;

    $scope.user = {};

    $scope.changeLanguage = function(lan) {
      $translate.use(lan);
    };

    $scope.registerUser = function() {

      if ($scope.processing) {
        return;
      }

      $scope.processing = true;
      var toRegister = {
        'name': $scope.user.name,
        'password': $scope.user.password,
        'phoneNumber': $scope.user.phoneNumber,
        'email': $scope.user.email
      };
      toRegister.address = {
        'streetAddress': $scope.user.streetAddress,
        'zipCode': $scope.user.zipCode,
        'city': $scope.user.city,
        'country': $scope.user.country
      };

      registrationService
        .registerNew(toRegister)
        .then(function(res) {
          if (res.success) {
            $scope.processing = false;
            $scope.registrationComplete = true;
          } else {
            $scope.processing = false;
            $translate(res.cause)
              .then(function(translatedCause) {
                $mdDialog.show(
                  $mdDialog.alert()
                    .title(translatedCause)
                    .ariaLabel(translatedCause)
                    .ok('OK')
                );
              });
          }
        });
    };

  }]);
