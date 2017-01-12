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

	it('sends the new user post request', function(done) {

		this.$httpBackend.when('POST', '/register/',
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