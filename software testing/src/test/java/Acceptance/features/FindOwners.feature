#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Find Owners
  As a user of the PetClinic Application,
  I wish to find particular owners by their last name
  So that I can view or edit their information.
  
    Background: 
     Given the following owners exist in the system:
      | firstName  | lastName | address           | city        | telephone  |
      | Betty      | Davis    | 638 Cardinal Ave. | Sun Prairie | 6085551749 |
      | Harold     | Davis    | 563 Friendly St.  | Windsor     | 6085553198 |
      | Jeff       | Black    | 1450 Oak Blvd.    | Monona      | 6085555387 |

 		Scenario: Find an existing owner (Normal Flow)
    When User searches an owners with specified "<lastName>"
    Then the following owner information shall be displayed:
    
      | firstName  | lastName | address           | city        | telephone  |
      | Jeff       | Black    | 1450 Oak Blvd.    | Monona      | 6085555387 |
      
      Examples: 
      | lastName  |
      | Black     |
      
    Scenario: Find existing owners (Alternative Flow)
    When User searches an owners with specified "<lastName>"
    Then the following owners shall be displayed in the list:
    
      | firstName  | lastName | address           | city        | telephone  |
      | Betty      | Davis    | 638 Cardinal Ave. | Sun Prairie | 6085551749 |
      | Harold     | Davis    | 563 Friendly St.  | Windsor     | 6085553198 |
      
      Examples: 
      | lastName  |
      | Davis     |
      
    Scenario: Find existing owners (Alternative Flow)
    When User searches an owners with specified " "
    Then the following owners shall be displayed in the list:
    
      | firstName  | lastName  | address               | city        | telephone  |
      | George     | Franklin	 | 110 W. Liberty St.    | Madison	   | 6085551023	|
      | Betty      | Davis     | 638 Cardinal Ave.     | Sun Prairie | 6085551749 |
      | Eduardo    | Rodriquez | 2693 Commerce St.	   | McFarland	 | 6085558763	|
      | Harold     | Davis     | 563 Friendly St.      | Windsor     | 6085553198 |
      | Peter      | McTavish	 | 2387 S. Fair Way	     | Madison	   | 6085552765 |
      | Jean       | Coleman	 | 105 N. Lake St.	     | Monona	     | 6085552654	|
      | Jeff       | Black	   | 1450 Oak Blvd.   	   | Monona	     | 6085555387 |
      | Maria      | Escobito	 | 345 Maple St.	       | Madison	   | 6085557683	|
      | David      | Schroeder | 2749 Blackhawk Trail	 | Madison	   | 6085559435 |
      | Carlos     | Estaban	 | 2335 Independence La. | Waunakee	   | 6085555487 |
      
    Scenario: Find non-existing owners (Error Flow)
    When User searches an owners with specified "<lastName>"
    Then the system shall raise the error "has not been found"
      
      Examples: 
      | lastName  |
      | Smith     |