Feature: Authentication API

  Background:
    Given the auth API is available

  Scenario: Login with valid credentials
    Given valid test credentials are configured
    When I login with the configured credentials
    Then the auth response status should be 200
    And the response should contain a non-empty access token
    And the response should contain a non-empty refresh token
    And the token type should be "Bearer"

  Scenario: Refresh an access token
    Given a valid refresh token is available
    When I refresh the access token
    Then the auth response status should be 200
    And the response should contain a non-empty access token
