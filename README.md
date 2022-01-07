# README

Testing the [Vaadin sample project address book](https://github.com/amonschau/tutorial).

This test implementation in branch "junit_cucumber" requires Selenium Grid (version 4) to be running in standalone setup.

## Set up

1. Clone from GitHub to a local directory using: `git clone https://github.com/bbccd/AddressBookTest.git`
2. Start the Vaadin address book from tutorial (see tutorial linked above under title). The tests work on the demo data; so, it is adviseable to restart the test object after each test execution to reset the demo data for best results.
3. Check and adapt the URL location of the running Vaadin address book (path and port; it is hard-coded in class `AddressBookHomePage`)
4. The project assumes that a Chromedriver is available, and that the system knows how to start it. Pontentially, adapt the constructor methods of the two test suite classes `FilterTest` and `CustomerAdministrationTest` accordingly)
5. Check and adapt the URL of the Selenium Grid hub/standalone service. It is hard-coded in all test runner classes (for the Cucumber tests, this is the file "StepDefinitions.java", for the jUnit tests, those are the files "FilterTest.java" and "CustomerAdministrationTest.java"; note, however, that the jUnit test cases currently are all deactivated, so that this setup of the Grid URL in the jUnit test classes currently is not used (can be skipped right now))
6. Start the test suites by running `mvn test`.


Note: the test suite may create screen shots (in the `target` directory of the Maven project). Consequently, `mvn clean` needs to be run after each run.