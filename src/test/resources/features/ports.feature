Feature: Port management

  Background:
    Given the port API is available
    And valid test credentials are configured
    And I am authenticated as a valid portal user
    Then the auth response status should be 200

  Scenario: Update an existing port
    Given an existing port is available
    When I update that port with its current values
    Then the port response status should be 200
    And the response should describe the requested port