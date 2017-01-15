/*jshint browserify: true */
'use strict';

var angular = require('angular');
require('angular-material');
require('angular-messages');
require('angular-translate');

angular.module('k15t-pat-registration', [
  'ngMaterial', 'ngMessages', 'pascalprecht.translate'
]);

require('./registrationService.js');
require('./registrationController.js');

/* setup translations */
// TODO translations should be included from some
// translations file
angular.module('k15t-pat-registration')
  .config(function($translateProvider) {
  $translateProvider.useSanitizeValueStrategy('escape');
  $translateProvider.translations('en', {
    'REGISTRATION_FORM': 'Registration Form',
    'EMAIL_ALREADY_REGISTERED': 'E-Mail already registered',
    'SERVICE_NOT_AVAILABLE': 'Service not available',
    'ERROR_IN_REGISTRATION': 'Error in registration'
  });
  $translateProvider.translations('de', {
    'REGISTRATION_FORM': 'Registrierungsformular'
  });
  $translateProvider.preferredLanguage('en');
  $translateProvider.fallbackLanguage('en');
});
