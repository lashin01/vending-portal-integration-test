Feature: Box Admin API

  Background:
    Given the box API is available
    And a box admin access token is available
    And an existing box id is configured

  Scenario: Update a box label
    Given a box label value is configured
    When I update the configured box label
    Then the box response status should be 200
    And the response should describe the configured box

  Scenario: Link a product to a box
    Given existing product identifiers are configured
    When I link the configured product to the box
    Then the box response status should be 200
    And the response should describe the configured box

  Scenario: Unlink a product from a box
    When I unlink the product from the configured box
    Then the box response status should be 200
    And the response should describe the configured box

  Scenario: Restock a box
    When I restock the configured box
    Then the box response status should be 200
    And the response should describe the configured box

  Scenario: Mark a box empty
    When I mark the configured box empty
    Then the box response status should be 200
    And the response should describe the configured box

  Scenario: Mark a box faulty
    When I mark the configured box faulty
    Then the box response status should be 200
    And the response should describe the configured box
