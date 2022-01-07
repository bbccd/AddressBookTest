Feature: Finding People in Address Book
  Using the filter feature allows to find people in the address book

  Scenario: Finding an existing person
    Given the address book
    When I filter for an existing user by last name
    Then I should find at least one result