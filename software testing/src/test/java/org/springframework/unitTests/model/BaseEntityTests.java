package org.springframework.unitTests.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.springframework.samples.petclinic.model.BaseEntity;

@DisplayName("Testing functions of BaseEntity Class")
public class BaseEntityTests {

	BaseEntity entity = new BaseEntity();

	@Test
	@DisplayName("Testing baseEntity creation")
	public void testBaseEntityConstructor() {
		assertNull(entity.getId());
		entity.setId(1);
		assertNotNull(entity.getId());
	}

	@Test
	@DisplayName("Testing setters and getters for id with a proper value")
	public void testSetIDAndGetIDSuccess() {
		entity.setId(1);
		assertEquals(1, entity.getId());
	}

	@Test
	@DisplayName("Testing setters getters for id with a non proper value")
	public void testSetIDAndGetIDFail() {
		entity.setId(1);
		Assertions.assertNotEquals(2, entity.getId());
		Assertions.assertNotNull(entity.getId());
	}

	@Test
	@DisplayName("Testing a function isNew when it's a new entity")
	public void testIsNewSuccessIfNew() {
		assertTrue(entity.isNew(), "This is a new entity");
	}

	@Test
	@DisplayName("Testing a function isNew when it's not a new entity")
	void testIsNewSuccessIfNotNew() {
		BaseEntity oldEntity = new BaseEntity();
		oldEntity.setId(2);
		assertFalse(oldEntity.isNew(), "This is not a new entity");
	}

}
