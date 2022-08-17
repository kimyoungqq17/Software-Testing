package org.springframework.unitTests.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.springframework.samples.petclinic.model.NamedEntity;

@DisplayName("Testing functions of NamedEntity Class")
public class NamedEntityTests {

	NamedEntity namedEntity = new NamedEntity();

	@Test
	@DisplayName("Testing NamedEntity creation")
	public void testNamedEntityConstructor() {
		assertNull(namedEntity.getId());
		namedEntity.setId(1);
		assertNotNull(namedEntity.getId());
	}

	@Test
	@DisplayName("Testing getters and setters for name with a proper value")
	public void testSetNameAndGetNameSuccess() {
		namedEntity.setName("Younggue");
		assertEquals("Younggue", namedEntity.getName());
	}

	@Test
	@DisplayName("Testing getters and setters for name with a non proper value")
	public void testSetNameAndGetNameFail() {
		namedEntity.setName("Younggue");
		Assertions.assertNotEquals("Kanye", namedEntity.getName());
	}

	@Test
	@DisplayName("Testing toString function with a proper value")
	public void testToStringSuccess() {
		namedEntity.setName("Younggue");
		assertEquals("Younggue", namedEntity.toString());
	}

	@Test
	@DisplayName("Testing toString function with a non proper value")
	public void testToStringFail() {
		namedEntity.setName("Younggue");
		Assertions.assertNotEquals("Kanye", namedEntity.toString());
	}

}
