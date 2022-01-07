Feature: Finding People in Address Book
  Using the filter feature allows to find people in the address book

  Scenario: Finding an existing customer by last name
    Given the address book
    When I filter for an existing customer by last name
    Then I should find at least one result

  Scenario: Finding an existing customer by last name
    Given the address book
    When I filter for an existing customer by first name
    Then I should find at least one result

  Scenario: Filtering for a non-existent customer returns an empty filter results list
    Given the address book
    When I filter for a non-existent customer by a name that is an arbitrary UUID
    Then I should find exactly 0 results

  Scenario: Filtering and then resetting the filter resets the display of the results list
    Given the address book
    When I filter for a non-existent customer by a name that is an arbitrary UUID
    And reset the filter
    Then I should find a large number of results in the results list