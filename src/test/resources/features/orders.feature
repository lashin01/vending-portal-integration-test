Feature: Order Admin API

  Background:
    Given the order API is available
    And an order admin access token is available

  Scenario: List orders
    When I list orders
    Then the order response status should be 200
    And the response body should be an order collection

  Scenario: List orders for a configured machine
    Given an existing machine id is configured
    When I list orders for the configured machine
    Then the order response status should be 200
    And the response body should be an order collection

  Scenario: Get a configured existing order
    Given an existing order id is configured
    When I get the configured order
    Then the order response status should be 200
    And the response should describe the configured order

  Scenario: List documents for a configured existing order
    Given an existing order id is configured
    When I list documents for the configured order
    Then the order response status should be 200
    And the response body should be an order document collection

  Scenario: Get a configured existing order document
    Given an existing order id is configured
    And an existing order document id is configured
    When I get the configured order document
    Then the order response status should be 200
    And the response body should contain document bytes

  Scenario: Create an order
    Given valid order creation data is configured
    When I create an order
    Then the order response status should be 200
    And the response should describe the created order

  Scenario: Mark an order paid
    Given an existing payable order id is configured
    When I mark the configured order paid
    Then the order response status should be 200
    And the response should describe the configured order

  Scenario: Dispense an order
    Given an existing dispensable order id is configured
    When I dispense the configured order
    Then the order response status should be 200
    And the response should describe the configured order

  Scenario: Complete an order
    Given an existing completable order id is configured
    When I complete the configured order
    Then the order response status should be 200
    And the response should describe the configured order

  Scenario: Fail an order
    Given an existing failable order id is configured
    When I fail the configured order
    Then the order response status should be 200
    And the response should describe the configured order

  Scenario: Cancel an order
    Given an existing cancelable order id is configured
    When I cancel the configured order
    Then the order response status should be 200
    And the response should describe the configured order
