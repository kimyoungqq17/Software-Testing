package org.springframework.unitTests.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Person;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTests {

	Person person = new Person();

	@Test
	@DisplayName("Testing Person creation")
	public void testNamedEntityConstructor() {
		assertNull(person.getId());
		person.setId(1);
		assertNotNull(person.getId());
	}

	@Test
	@DisplayName("success case of testing setters and getters for firstName")
	public void testGetFirstNameAndSetFirstNameSuccess() {
		person.setFirstName("Younggue");
		assertEquals("Younggue", person.getFirstName());
	}

	@Test
	@DisplayName("failure case of testing setters and getters for firstName")
	public void testGetFirstNameAndSetFirstNameFailure() {
		person.setFirstName("Younggue");
		assertNotEquals("Kanye", person.getFirstName());
	}

	@Test
	@DisplayName("success case of testing setters and getters for lastName")
	public void testGetLastNameAndSetLastNameSuccess() {
		person.setLastName("Kim");
		assertEquals("Kim", person.getLastName());
	}

	@Test
	@DisplayName("failure case of testing setters and getters for lastName")
	public void testGetLastNameAndSetLastNameFailure() {
		person.setLastName("Kim");
		assertNotEquals("West", person.getLastName());
	}

}
