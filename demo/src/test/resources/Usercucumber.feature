Feature: User Management

  @WithAdminRole
  Scenario: Create a new user
    Given a user with ID 3 exists
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
        "id": 0,
        "name": "Alice",
        "email": "alice@example.com"

      }
      """
  @WithAdminRole
  Scenario: Update an existing user
    Given a user with ID 3 exists
    When the client sends a PUT request to "/user/3" with the following JSON:
      """
      {
        "id": 3,
        "name": "Updated Alice",
        "email": "updated.alice@example.com"

      }
      """
    Then the response status code should be 200
    And the response JSON should contain:
      """
      {
        "id": 3,
        "name": "Updated Alice",
        "email": "updated.alice@example.com"
      }
      """
