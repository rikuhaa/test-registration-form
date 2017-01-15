/*jshint browserify: true */
'use strict';

var angular = require('angular');

angular.module('k15t-pat-registration')
  .factory('registrationService', ['$http', '$q',
    function($http, $q) {

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

      var methods = {
        'registerNew': registerNew
      };

      return methods;
    }
  ]);
