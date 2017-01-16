Registration Form
===================

A test / learning project implementing a simple registration form with Maven/Spring Boot/Jersey on the back-end side, and Gulp/Angular/Angular Material on the front-end side.

----------


Documents
-------------

###Build
* Run in the root directory eg. 'mvn package'
  * Downloads and installs (locally) all the required dependencies, including node for front-end code, and builds the project
* For building JavaDoc (and other reports), run 'mvn site'

### Run
* Run the the jar generated to 'target folder'
  * From project root: 'java -jar target/registration-0.1.0.jar'
* Open web browser to localhost:8080


Developing
-------------
### Front-end
* Front-end code is in folder src/main/frontend
* The front-end can be developed independently from the back-end by running node.js directly (without the Maven control)
* Gulp tasks can be invoked by eg. running 'npm run gulp "task"' (with correct "task" after running 'npm install' in the src/main/frontend -folder)
* Most important development gulp tasks are
  * tdd : runs all the unit tests and re-runs on source changes (for debugging tests would be helpful to change the test runner from PhantomJS to eg. Chrome)
  * develop : opens a ligth weight webserver for the front-end code and reloads on source changes
  * jscs : style check
  * jshint : js linting (static code check for probable errors)
  * test : can be done for running the tests once and creating a code coverage report of the tests (can be found in gen/Phantom .../index.html)

### Back-end
* The back-end code is laid out in a typical Maven way
* When only doing back-end code development with the help of eg. 'mvn test', it makes sense to use -Dskip.gulp or -Dskip.npm to skip the relatively slow front-end code build and test tasks
* Some extra Maven tasks
  * Also the back-end build contains code coverage reporting. The report can be generated by running 'mvn jacoco:report' (after running 'mvn test') and the report can be found in target/site/jacoco folder
  * Running 'mvn site' will generate a report of the project that will also include JavaDoc and a FindBugs static code analysis report


TODO
-------------
### Features
* Integrate an option to registrate with using eg. Facebook
* More tests
  * Especially end-to-end tests with eg. Protractor
* Back-end build improvements
  * Performance (eg. some of the heavier Spring tests could probably be run concurrently)
  * Integrate a style checker
  * Some level of code coverage could be enforced by JaCoCo
* Front-end build improvements
   * Integrate a doc-generation tool (eg. ngdocs)
   * Minify the js and css (could be done at least when an env variable 'prod' is set when running gulp)
   * Source maps
* Validation improvement
   * Could integrate passay etc. tool for checking password strength
   * (at least) in front-end, could use some api (google?) to provide help for typing the correct address
  
### To discuss
* Validation of registrations
  * Some locality based required formats for eg. address, phone number etc?
  * Generally hard to give any universally correct pattern, and on the other hand it would still typically be easy for a user to type invalid information that matches the pattern (so user has to be trusted a bit here)
  * As always, the front-end validation is mostly for helping the user (so eg. some address service could help). And the back-end is for validating data (front-end validation can be by-passed quite easily), so in the back-end there should at least be some data length limits.
* Whether to break builds on too low code coverage and what should the treshold be
  * Code coverage analysis is a useful tool and especially having a look at the report at times gives useful information
  * Still only one measure of good testing that misses many aspects (eg. side effects, or some important input value cases)
