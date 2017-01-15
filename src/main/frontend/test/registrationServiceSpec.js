/*jshint jasmine: true */
/*globals angular: false */

describe('registrationService', function() {

	beforeEach(function() {

		angular.mock.module('k15t-pat-registration');

		angular.mock.inject(function(_registrationService_, 
			_$httpBackend_) {
			this.registrationService = _registrationService_;
			this.$httpBackend = _$httpBackend_;
		});

	});

	it('handles valid registration REST call', function(done) {

		this.$httpBackend.when('POST', '/rest/registration',
			function(postData) {
        jsonData = JSON.parse(postData);
        expect(jsonData.name).toEqual('testName');
        expect(jsonData.email).toEqual('testEmail');
        expect(jsonData.address).toBeTruthy();
        expect(jsonData.address.street).toEqual('testStreet');
        expect(jsonData.address.city).toEqual('testCity');
        return true;
      }
    ).respond(200, true);

		var res = this.registrationService.registerNew({
      'name': 'testName',
      'email': 'testEmail',
      'address': {
        'street': 'testStreet',
        'city': 'testCity'
      }
    });    

    res.then(function(result) {
      expect(result.success).toBeTruthy();
      done();
    });

    this.$httpBackend.flush();

	});

  it('handles informing already registered', function(done) {

    // handle conflict (ie. user already added)
    this.$httpBackend.when('POST', '/rest/registration',
      function(postData) {
        jsonData = JSON.parse(postData);
        expect(jsonData).toEqual('test')
        return true;
      }
    ).respond(409, true);

    var res = this.registrationService.registerNew('test');    

    res.then(function(result) {
      expect(result.success).toBeFalsy();
      expect(result.cause).toEqual('EMAIL_ALREADY_REGISTERED');
      done();
    });

    this.$httpBackend.flush();

  });

  it('handles informing invalid data / general failure', function(done) {

    // handle invalid data (user of the method should validate the data)
    this.$httpBackend.when('POST', '/rest/registration',
      function(postData) {
        jsonData = JSON.parse(postData);
        expect(jsonData).toEqual('test')
        return true;
      }
    ).respond(400, true);

    var res = this.registrationService.registerNew('test');    

    res.then(function(result) {
      expect(result.success).toBeFalsy();
      expect(result.cause).toEqual('ERROR_IN_REGISTRATION');
      done();
    });

    this.$httpBackend.flush();

  });

  // handle service down / 404
  it('handles informing service down', function(done) {

    // handle conflict (ie. user already added)
    this.$httpBackend.when('POST', '/rest/registration',
      function(postData) {
        jsonData = JSON.parse(postData);
        expect(jsonData).toEqual('test')
        return true;
      }
    ).respond(404, true);

    var res = this.registrationService.registerNew('test');    

    res.then(function(result) {
      expect(result.success).toBeFalsy();
      expect(result.cause).toEqual('SERVICE_NOT_AVAILABLE');
      done();
    });

    this.$httpBackend.flush();

  });

});