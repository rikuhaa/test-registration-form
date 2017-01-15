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

  it('sends check for already registered email', function(done) {

    this.$httpBackend.when('GET', '/rest/registration/test@test.com').respond(200, true);

    var res = this.registrationService.isAlreadyRegistered('test@test.com');    

    res.then(function(result) {
      expect(result.success).toBeTruthy();
      done();
    });

    this.$httpBackend.flush();

  });

  it('isAlreadyRegistered() handles failing calls', function(done) {

    this.$httpBackend.when('GET', '/rest/registration/test@test.com').respond(404, true);

    var res = this.registrationService.isAlreadyRegistered('test@test.com');

    res.then(function(result) {
      expect(result.success).toBeFalsy();
      done();
    });

    this.$httpBackend.flush();

  });

	it('sends the new user post request', function(done) {

		this.$httpBackend.when('POST', '/rest/registration',
			function(postData) {
        jsonData = JSON.parse(postData);
        //expect(jsonData.message).toBe(post.storyMessage);
        //expect(jsonData.post_fb).toBe(post.postToFB);
        //expect(jsonData.post_twitter).toBe(post.postToTwitter);
        //expect(jsonData.post_team).toBe(post.postToTeam);
        return true;
      }
    ).respond(200, true);

		var res = this.registrationService.registerNew('test');    

    res.then(function(result) {
      expect(result.success).toBeTruthy();
      done();
    });

    this.$httpBackend.flush();

	});

});