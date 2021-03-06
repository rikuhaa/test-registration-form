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

    // model object to which the form fields are stored
    $scope.user = {};

    $scope.changeLanguage = function(lan) {
      $translate.use(lan);
    };

    // takes the values enter into the registration form
    // (these should be validated before calling this
    // method) and calls the service method to do the
    // registration
    // informs the user about the registration result
    $scope.registerUser = function() {

      if ($scope.processing) {
        return;
      }

      // TODO this should be done with a custom validator
      // directive (plenty of examples in the net)
      if (!($scope.user.password === $scope.user.passwordRep &&
        $scope.user.email === $scope.user.emailRep)) {
        $mdDialog.show(
          $mdDialog.alert()
            .title('E-mails or passwords not identical')
            .ok('OK')
        );
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

    $scope.showAllRegistered = function() {

      registrationService
        .getAllRegistered()
        .then(function(res) {
          $mdDialog.show(
            $mdDialog.alert()
              .title('All registered')
              .textContent(res.response)
              .ok('OK')
            );
        });

    };

  }]);
