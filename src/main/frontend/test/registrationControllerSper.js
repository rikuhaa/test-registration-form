/*jshint jasmine: true */
/*globals angular: false */

describe('registrationController', function() {

  beforeEach(function() {

    angular.mock.module('k15t-pat-registration');

    angular.mock.inject(function(_$rootScope_, 
      _$controller_) {
      this.$rootScope = _$rootScope_;
      this.$controller = _$controller_;

      this.$scope = this.$rootScope.$new();

      this.registrationController = this.$controller(
        'registrationController', {
          $scope: this.$scope
        });

    });

  });

  it('initially has falsy "registrationComplete"', function() {
    expect(this.$scope.registrationComplete).toBeFalsy();
  });


});