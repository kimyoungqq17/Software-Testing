package org.springframework.unitTests.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.ui.ModelMap;
import org.springframework.util.SerializationUtils;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class VisitTests {

	Visit visit = new Visit();

	@Test
	@DisplayName("Testing visit creation")
	public void testVisitConstructor() {
		assertNull(visit.getPetId());
		visit.setId(1);
		visit.setDescription("Doggo too chubby");
		visit.setDate(LocalDate.now());
		Pet pet = new Pet();
		pet.setId(1);
		visit.setPetId(1);
		assertNotNull(visit.getPetId());
		assertNotNull(visit.getDate());
		assertNotNull(visit.getId());
		assertNotNull(visit.getDescription());
	}

	@Test
	@DisplayName("Testing getters and setters for date with a proper value")
	public void testSetDateAndGetDateSuccess() {
		LocalDate today = LocalDate.now();
		visit.setDate(LocalDate.now());
		assertEquals(today, visit.getDate());
	}

	@Test
	@DisplayName("Testing getters and setters for date with a non proper value")
	public void testSetDateAndGetDateFail() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = LocalDate.now().minusDays(1);
		visit.setDate(LocalDate.now());
		assertNotEquals(yesterday, visit.getDate());
	}

	@Test
	@DisplayName("Testing getters and setters for descriptoin with a proper value")
	public void testSetDateAndGetDescriptionSuccess() {
		visit.setDescription("Doggo is too chubby");
		assertEquals("Doggo is too chubby", visit.getDescription());
	}

	@Test
	@DisplayName("Testing getters and setters for descriptoin with a non proper value")
	public void testSetDateAndGetDescriptionFail() {
		visit.setDescription("Doggo won't sleep");
		assertNotEquals("Doggo is too chubby", visit.getDescription());
	}

	@Test
	@DisplayName("Testing getters and setters for petId with a proper value")
	public void testSetDateAndGetPetIdSuccess() {
		Pet pet = new Pet();
		pet.setId(1);
		visit.setPetId(1);
		assertEquals(pet.getId(), visit.getPetId());
	}

	@Test
	@DisplayName("Testing getters and setters for petId with a non proper value")
	public void testSetDateAndGetPetIdFail() {
		Pet pet = new Pet();
		pet.setId(1);
		Pet diffPet = new Pet();
		diffPet.setId(2);
		visit.setPetId(1);
		assertNotEquals(diffPet.getId(), visit.getPetId());
	}

}
