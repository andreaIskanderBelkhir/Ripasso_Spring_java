
Feature: User Management

  #@WithAdminRole
  Scenario:Create new user and add to db
    When creo utente con nome "alice" e email "alice@example.com"
    Then utente "alice" è presente nel db

  #@WithAdminRole
  Scenario:Removed user from db
    When creo utente con nome "alice" e email "alice@example.com"
    Then utente "alice" è presente nel db
    When rimuovo utente "alice" dal db
    Then utente "alice" non è presente nel db

  #@WithAdminRole
  Scenario:Update user from db
    When creo utente con nome "alice" e email "alice@example.com"
    Then utente "alice" è presente nel db
    And utente "alice" ha email "alice@example.com"
    When aggiorno email "alice2@example.it" ad "alice"
    Then utente "alice" ha email "alice2@example.it"

