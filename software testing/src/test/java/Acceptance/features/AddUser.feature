Feature: Add a new owner
As a user of the PetClinic Application
I would like to add a new owner, 
so that this particular owner can book appointments for their pets. 

  Scenario: Add a new owner
    Given the following information for an owner
    | firstName | lastName | id | address           | city     | telephone |
    | Captain   | Morgan   | 11 | 123 McGill Avenue | Montreal | 5148970943|
    When the user requests to add a new owner
    Then the new owner should be added 

  Scenario: Add a new owner with missing first name field
	  Given the following information for an owner 
	   | firstName | lastName | id | address           | city     | telephone |
	   |           | Daniels  | 15 | 123 McGill Avenue | Montreal | 5148970943|
	  When the user requests to add a new owner
	  Then the new owner should not be added
	  And  an error message should be displayed under the first name field
	  
  Scenario: Add a new owner with missing last name field
	  Given the following information for an owner 
	   | firstName | lastName | id | address           | city     | telephone |
	   | Jack      |          | 15 | 123 McGill Avenue | Montreal | 5148970943|
	  When the user requests to add a new owner
	  Then the new owner should not be added
	  And  an error message should be displayed under the last name field
  
  
  Scenario: Add a new owner with missing city field
   Given the following information for an owner 
	   | firstName | lastName | id | address           | city     | telephone |
	   | Jack      | Daniels  | 15 | 123 McGill Avenue |          | 5148970943|
	  When the user requests to add a new owner
	  Then the new owner should not be added
	  And  an error message should be displayed under the city field
	  
	Scenario: Add a new owner with missing telephone field
   Given the following information for an owner 
	   | firstName | lastName | id | address           | city     | telephone |
	   | Jack      | Daniels  | 15 | 123 McGill Avenue | Montreal | 					|
	  When the user requests to add a new owner
	  Then the new owner should not be added
	  And  an error message should be displayed under the telephone field
	  
	Scenario: Add a new owner with missing address field
   Given the following information for an owner 
	   | firstName | lastName | id | address           | city     | telephone |
	   | Jack      | Daniels  | 15 |                   | Montreal | 5148970943|
	  When the user requests to add a new owner
	  Then the new owner should not be added
	  And  an error message should be displayed under the address field
  