
Feature: User Management

  @WithAdminRole
  Scenario:Create new user and add to db
    When creo utente con nome "alice" e email "alice@example.com"
    Then utente "alice" è presente nel db

  @WithAdminRole
  Scenario:Removed user from db
    When creo utente con nome "alice" e email "alice@example.com"
    Then utente "alice" è presente nel db
    When rimuovo utente "alice" dal db
    Then utente "alice" non è presente nel db

  @WithAdminRole
  Scenario:Update user from db
    When creo utente con nome "alice" e email "alice@example.com"
    Then utente "alice" è presente nel db
    And utente "alice" ha email "alice@example.com"
    When aggiorno email "alice2@example.it" ad "alice"
    Then utente "alice" ha email "alice2@example.it"

    #commenta qui sotto
  @WithAdminRole
  Scenario: Create a new user
    When creo un utente con i dati:
      """
      {
        "name": "Alice",
        "email": "alice@example.com"
      }
      """
    Then la risposta è "ok"
    And la risposta del server è:
      """
      {
        "id": 0,
        "name": "Alice",
        "email": "alice@example.com"

      }
      """
  @WithAdminRole
  Scenario: Update an existing user
    Given un utente avente il campo :"id" con valore : "3"
    When il cliente vuole aggiornare un user con campo : "id" e valore : "3" usando i dati:
      """
      {
        "id": 3,
        "name": "Updated Alice",
        "email": "updated.alice@example.com"

      }
      """
    Then la risposta è "ok"
    And la risposta del server è:
      """
      {
        "id": 3,
        "name": "Updated Alice",
        "email": "updated.alice@example.com"
      }
      """
  @WithAdminRole
  Scenario: Get user that exist
    Given un utente avente il campo :"id" con valore : "3"
    When il cliente vuole vedere un user con campo : "id" e valore : "3"
    Then la risposta è "ok"
    And la risposta del server è:
      """
      {
        "id": 3,
        "name": "John Doe",
        "email": "john.doe@example.com"
      }
      """
  @WithAdminRole
  Scenario: Get user that doesnt exist
    Given un utente avente il campo :"id" con valore : "3" non esiste
    When il cliente vuole vedere un user con campo : "id" e valore : "3"
    Then la risposta è "non trovato"

  @WithAdminRole
  Scenario: Get user that exist with mail
    Given un utente avente il campo :"Email" con valore : "john.doe@example.com"
    When il cliente vuole vedere un user con campo : "mail" e valore : "john.doe@example.com"
    Then la risposta è "ok"
    And la risposta del server è:
      """
      {
        "id": 3,
        "name": "john doe",
        "email": "john.doe@example.com"
      }
      """
  @WithAdminRole
  Scenario: Delete a user
    Given un utente avente il campo id con valore : 20 deve essere presente
    When cancello utente con id : "20"
    Then la risposta è "ok"