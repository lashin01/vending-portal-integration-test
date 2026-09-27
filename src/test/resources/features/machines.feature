Feature: Machine management

  Background:
    Given I am authenticated as a valid portal user

  Scenario: List machines
    When I request the list of machines
    Then the machine response status should be 200

  Scenario: Get an existing machine
    Given an existing machine is available
    When I request that machine
    Then the machine response status should be 200
    And the response should describe the requested machine

  Scenario: Get machine topology
    Given an existing machine is available
    When I request the topology of that machine
    Then the machine topology response status should be 200