/*jshint jasmine: true */
/*globals angular: false */

describe('registrationController', function() {

  beforeEach(function() {

    angular.mock.module('k15t-pat-registration');

    angular.mock.inject(function(_$rootScope_, 
      _$controller_, _registrationService_,
      _$mdDialog_) {
      this.$rootScope = _$rootScope_;
      this.$controller = _$controller_;
      this.registrationService = _registrationService_;
      this.$mdDialog = _$mdDialog_;

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

  it('delegates registration calls correctly', function() {
    var registerNewRes = jasmine.createSpyObj(
      'registerNew', ['then']);
    var thenCallback = null;
    spyOn(this.registrationService, 'registerNew')
      .and.returnValue({'then': function(callback) {
        thenCallback = callback;
      }});

    this.$scope.registerUser();
    expect(this.registrationService.registerNew).toHaveBeenCalled();

    expect(thenCallback).not.toBeFalsy();

    spyOn(this.$mdDialog, 'show');

    thenCallback.call(null, {success: false});
    expect(this.$mdDialog.show).toHaveBeenCalled();

    this.$mdDialog.show.calls.reset();

    thenCallback.call(null, {success: true});
    expect(this.$mdDialog.show).toHaveBeenCalled();
  });


});