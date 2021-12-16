# README

Testing the [Vaadin sample project address book](https://github.com/amonschau/tutorial).

## Set up

1. Clone from GitHub to a local directory using: 'git clone https://github.com/bbccd/AddressBookTest.git'
2. Start the Vaadin address book from tutorial (see tutorial linked above under title). The tests work on the demo data; so, it is adviseable to restart the test object after each test execution to reset the demo data for best results.
3. Check and adapt the URL location of the running Vaadin address book (path and port; it is hard-coded in class 'AddressBookHomePage')
4. The project assumes that a Chromedriver is available, and that the system knows how to start it. Pontentially, adapt the constructor methods of the two test suite classes 'FilterTest' and 'CustomerAdministrationTest' accordingly)
5. Start the test suites by running 'mvn test'.


Note: the test suite creates screen shots (in the 'target' directory of the Maven project). Consequently, 'mvn clean' needs to be run after each run.