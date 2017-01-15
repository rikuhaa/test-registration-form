/*jshint jasmine: true */
/*globals angular: false */

describe('registrationController', function() {

  beforeEach(function() {

    angular.mock.module('k15t-pat-registration');

    angular.mock.inject(function(_$rootScope_, 
      _$controller_, _registrationService_,
      _$mdDialog_, _$translate_) {
      this.$rootScope = _$rootScope_;
      this.$controller = _$controller_;
      this.registrationService = _registrationService_;
      this.$mdDialog = _$mdDialog_;
      this.$translate = _$translate_;

      this.$scope = this.$rootScope.$new();

      this.registrationController = this.$controller(
        'registrationController', {
          $scope: this.$scope
        });

    });

  });

  it('delegates change langugae to angular-translate', function() {

    spyOn(this.$translate, 'use');

    this.$scope.changeLanguage('fi');

    expect(this.$translate.use).toHaveBeenCalledWith('fi');

    this.$scope.changeLanguage('en');

    expect(this.$translate.use).toHaveBeenCalledWith('en');

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
    expect(this.$scope.processing).toBeTruthy();

    thenCallback.call(null, {success: false});
    expect(this.$scope.processing).toBeFalsy();
    expect(this.$scope.registrationComplete).toBeFalsy();

    thenCallback.call(null, {success: true});
    expect(this.$mdDialog.show).toBeTruthy();
  });

});