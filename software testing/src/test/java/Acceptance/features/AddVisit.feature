Feature: Add Visit 
  As a user of the PetClinic Application, 
  I would like to create a new appointment for an owners pet,
 	So that this particular pet can be treated by a veterinarian.

  Scenario Outline: Add Visit (Normal Flow)
    Given the following information about a new visit 
	    | description | petId | 
	    | neutered    | 1     |
    And the new visit has the date field specified as follows
	    | 2021-12-19 |
    When  the user submits the add visit form
  	Then  the visit should be added to the database


	Scenario Outline: Add Visit with missing date (Alternative Flow)
    Given the following information about a new visit 
	     | description | petId | 
		   | rabies      | 7     |
    And the new visit has the date field specified as follows
    	| [blank] |
    When the user submits the add visit form
    Then the visit should be added to the database
    
  Scenario Outline: Add Visit with missing description ( Alternative Flow)
     Given the following information about a new visit  
		     | description | petId | 
			   |             | 7     | 
			And the new visit has the date field specified as follows
    			| 2021-12-21 | 
     When the user submits the add visit form
 	   Then the visit should not be added to the database
     And  the system will redisplay the form page
     And  an error message under the description field will be displayed.
   
   Scenario Outline: Add Visit with missing description and missing date ( Alternative Flow)
     Given the following information about a new visit  
		     | description | petId | 
			   |             | 7     | 
			And the new visit has the date field specified as follows
    	   |    [blank]  | 
     When the user submits the add visit form
 	   Then the visit should not be added to the database
     And  the system will redisplay the form page
     And  an error message under the description field will be displayed.
   
   Scenario Outline: Add Visit with wrong date format (Alternative Flow)
  	 Given the following information about a new visit  
		     | description | petId | 
			   | rabies      | 7     | 
			And the new visit has the date field specified as follows
    		| 20211134 | 
     When the user submits the add visit form
 	   Then the visit should not be added to the database
     And  the system will redisplay the form page
     
    Scenario Outline: Add Visit with missing description and wrong date format (Alternative Flow)
		Given the following information about a new visit  
		     | description | petId | 
			   |             | 10    | 
		And the new visit has the date field specified as follows
    		| 23 November 2021 | 
     When the user submits the add visit form
 	   Then the visit should not be added to the database
     And  the system will redisplay the form page
     And  an error message under the description field will be displayed.