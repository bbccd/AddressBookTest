Feature: Administrating Customers in Address Book
  The administration feature should allows to create, update and delete customers in the address book

  Scenario: Creating a new customer, saving and filtering it again returns exactly one result
    Given the address book
    When I click on Add New Customer
    And input a random first name
    And input a random last name
    And input a random email address
    And input a status of Imported Lead
    And input a random birthday
    And click Save
    And reset the filter
    And filter again by the random first and last names
    Then I should find exactly one result
    And all values match the random ones used