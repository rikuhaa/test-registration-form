/*jshint browserify: true */
'use strict';

var angular = require('angular');

angular.module('k15t-pat-registration')
  .factory('registrationService', ['$http', '$q', '$log',
    function($http, $q, $log) {

      var registerNew = function(toRegister) {
        var userData = JSON.stringify(toRegister);
        var defer = $q.defer();
        $http.post('/rest/registration', userData)
          .then(
          // success
          function(response) {
            defer.resolve({
              'success': true
            });
          },
          // failure
          function(response) {
            var cause = '';
            if (response.status == 409) {
              cause = 'EMAIL_ALREADY_REGISTERED';
            } else if (response.status == 404) {
              cause = 'SERVICE_NOT_AVAILABLE';
            } else {
              // handle also validation errors here,
              // should not happen without bypassing client code
              cause = 'ERROR_IN_REGISTRATION';
            }
            defer.resolve({
              'success': false,
              'cause': cause
            });
          });
        return defer.promise;
      };

      var getAllRegistered = function() {

        var defer = $q.defer();
        $http.get('/rest/registration')
          .then(
          // success
          function(response) {
            defer.resolve({
              'response': response.data
            });
          },
          // failure
          function(response) {
            defer.resolve({
              'response': 'error: ' + response.status
            });
          });
        return defer.promise;
      };

      var methods = {
        'registerNew': registerNew,
        'getAllRegistered': getAllRegistered
      };

      return methods;
    }
  ]);
