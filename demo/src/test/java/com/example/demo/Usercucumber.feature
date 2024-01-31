Feature: User Management

  Scenario: Create a new user
    Given a user with ID 1 exists
    When the client sends a POST request to "/user" with the following JSON:
      """
      {
        "name": "Alice",
        "email": "alice@example.com"
      }
      """
    Then the response status code should be 200
    And the response JSON should contain:
      """
      {
        "id": 1,
        "name": "Alice",
        "email": "alice@example.com"
        "password": "test1"
      }
      """

  Scenario: Update an existing user
    Given a user with ID 2 exists
    When the client sends a PUT request to "/user/2" with the following JSON:
      """
      {
        "name": "Updated Alice",
        "email": "updated.alice@example.com"

      }
      """
    Then the response status code should be 200
    And the response JSON should contain:
      """
      {
        "id": 2,
        "name": "Updated Alice",
        "email": "updated.alice@example.com"
      }
      """
