/*jshint browserify: true */
'use strict';

var angular = require('angular');

angular.module('k15t-pat-registration')
  .factory('registrationService', ['$http', '$q',
    function($http, $q) {
      var methods = {
        'registerNew': function(name) {
          var userData = {'name': name};
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
        }
      };

      return methods;
    }
  ]);
