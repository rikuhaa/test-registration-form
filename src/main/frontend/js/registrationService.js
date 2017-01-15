/*jshint browserify: true */
'use strict';

var angular = require('angular');

angular.module('k15t-pat-registration')
  .factory('registrationService', ['$http', '$q',
    function($http, $q) {

      var registerNew = function(toRegister) {
        var userData = JSON.stringify(toRegister);
        var defer = $q.defer();
        $http.post('/register/', userData)
          .success(function(data) {
            defer.resolve({
              'success': true,
              'data': data
            });
          })
          .error(function() {
            defer.resolve({
              'success': false
            });
          });
        return defer.promise;
      };

      var isAlreadyRegistered = function(email) {

        var defer = $q.defer();

        $http.get('/register/' + email)
          .success(function(data) {
            defer.resolve({
              'success': true,
              'data': data
            });
          })
          .error(function() {
            defer.resolve({
              'success': false
            });
          });
        return defer.promise;

      };

      var methods = {
        'registerNew': registerNew,
        'isAlreadyRegistered': isAlreadyRegistered
      };

      return methods;
    }
  ]);
