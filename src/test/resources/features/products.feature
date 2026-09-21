Feature: Product Admin API

  Background:
    Given the product API is available
    And a product admin access token is available

  Scenario: List products
    When I list products
    Then the product response status should be 200
    And the response body should be a product collection

  Scenario: Get a configured existing product
    Given an existing product id is configured
    When I get the configured product
    Then the product response status should be 200
    And the response should describe the requested product

  Scenario: Create a product from configured request data
    Given valid product creation data is configured
    When I create a product
    Then the product response status should indicate successful creation
    And the response should describe the created product

  Scenario: Delete a configured existing product
    Given an existing deletable product id is configured
    When I delete the configured product
    Then the product response status should indicate success

  Scenario: List ecommerce-available products
    When I list ecommerce-available products
    Then the product response status should be 200
    And the response body should be a product collection
