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
    'ERROR_IN_REGISTRATION': 'Error in registration',
    'REGISTRATION_COMPLETED': 'Registration completed',
    'YOU_WILL_RECEIVE_CONFIRMATION_EMAIL_SOON':
      'You will receive confirmation e-mail soon',
    'NAME': 'Name',
    'PASSWORD': 'Password',
    'REPEAT_PASSWORD': 'Repeat password',
    'EMAIL': 'E-mail',
    'REPEAT_EMAIL': 'Repeat e-mail',
    'PHONE_NUMBER': 'Phone number',
    'STREET_ADDRESS': 'Street address',
    'CITY': 'City',
    'COUNTRY': 'Country',
    'ZIP_CODE': 'Zip code',
    'REGISTER': 'Register'
  });
  $translateProvider.translations('de', {
    'REGISTRATION_FORM': 'Registrierungsformular'
    // TODO
  });
  $translateProvider.preferredLanguage('en');
  $translateProvider.fallbackLanguage('en');
});
