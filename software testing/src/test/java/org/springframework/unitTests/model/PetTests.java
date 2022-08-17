package org.springframework.unitTests.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetTests {

	Pet pet = new Pet();

	PetType doggo = new PetType();

	Owner owner = new Owner();

	@Test
	@DisplayName("Testing pet creation")
	public void testPetConstructor() {
		assertNull(pet.getId());
		assertNull(pet.getType());
		assertNull(pet.getOwner());
		assertNull(pet.getName());
		assertNull(pet.getBirthDate());

		pet.setName("Norange");
		pet.setId(1);
		pet.setOwner(owner);
		pet.setType(doggo);
		pet.setBirthDate(LocalDate.now());

		assertNotNull(pet.getId());
		assertNotNull(pet.getType());
		assertNotNull(pet.getOwner());
		assertNotNull(pet.getName());
		assertNotNull(pet.getBirthDate());
	}

	@Test
	@DisplayName("Testing getters and setters for date with a proper value")
	public void testSetDateAndGetDateSuccess() {
		pet.setBirthDate(LocalDate.now());
		assertEquals(LocalDate.now(), pet.getBirthDate());
	}

	@Test
	@DisplayName("Testing getters and setters for date with a non proper value")
	public void testSetDateAndGetDateFail() {
		pet.setBirthDate(LocalDate.now());
		LocalDate rightDate = LocalDate.now();
		LocalDate wrongDate = rightDate.plusDays(2);
		Assertions.assertNotEquals(wrongDate, pet.getBirthDate());
	}

	@Test
	@DisplayName("Testing getters and setters for type with a proper value")
	public void testSetTypeAndGetTypeSuccess() {
		pet.setType(doggo);
		assertEquals(doggo, pet.getType());
	}

	@Test
	@DisplayName("Testing getters and setters for type with a non proper value")
	public void testSetTypeAndGetTypeFail() {
		Pet withNoType = new Pet();
		assertNull(withNoType.getType());
	}

	@Test
	@DisplayName("Testing getters and setters for owner with a proper value")
	public void testSetTypeAndGetOwnerSuccess() {
		Owner owner = new Owner();
		pet.setOwner(owner);
		assertEquals(owner, pet.getOwner());
	}

	@Test
	@DisplayName("Testing getters and setters for owner with a non proper value")
	public void testSetTypeAndGetOwnerFail() {
		Owner owner1 = new Owner();
		Owner owner2 = new Owner();
		pet.setOwner(owner2);
		Assertions.assertNotEquals(owner1, pet.getOwner());
	}

	@Test
	@DisplayName("Testing getters and setters for visitInternal with a non proper value")
	public void testSetVisitInternalAndGetVisitInternalForNullVisit() {
		Pet withNoVisit = new Pet();
		assertTrue(withNoVisit.getVisitsInternal().isEmpty());
		assertNotNull(withNoVisit.getVisitsInternal());

		Visit visit1 = new Visit();
		assertEquals(0, withNoVisit.getVisitsInternal().size());
		assertTrue(withNoVisit.getVisitsInternal().isEmpty());
		assertTrue(withNoVisit.getVisitsInternal().add(visit1));
		assertEquals(1, withNoVisit.getVisitsInternal().size());
	}

	@Test
	@DisplayName("Testing getters and setters for visitInternal with a proper value")
	public void testSetVisitInternalsAndGetVisitInternals() {
		Pet tempPet = new Pet();
		tempPet.setId(1);
		Visit visit1 = new Visit();
		visit1.setPetId(1);
		visit1.setDate(LocalDate.now());
		Set<Visit> visits = new HashSet<Visit>();
		visits.add(visit1);
		assertTrue(visits.contains(visit1));
		tempPet.setVisitsInternal(visits);
		assertTrue(tempPet.getVisitsInternal().equals(visits));
	}

	@Test
	@DisplayName("Testing a function getVisits()")
	public void testGetVisits() {
		Pet tempPet = new Pet();
		Visit visit1 = new Visit();
		Visit visit2 = new Visit();
		Set<Visit> visits = new HashSet<>();
		visits.add(visit1);
		visits.add(visit2);
		tempPet.setVisitsInternal(visits);
		List<Visit> testGetVisits = tempPet.getVisits();

		assertFalse(testGetVisits.isEmpty());
		assertEquals(2, testGetVisits.size());

		List<Visit> myVisitSorted = new ArrayList<>();
		myVisitSorted.add(visit1);
		myVisitSorted.add(visit2);
		List<Visit> myVisitUnsorted = new ArrayList<>();
		myVisitUnsorted.add(visit2);
		myVisitUnsorted.add(visit1);
		assertTrue(myVisitSorted.equals(testGetVisits));
		assertFalse(myVisitUnsorted.equals((testGetVisits)));

	}

	@Test
	@DisplayName("Testing a function addVisit")
	public void testAddVisit() {
		Pet tempPet = new Pet();
		tempPet.setId(1);
		Visit visit1 = new Visit();
		visit1.setPetId(1);
		visit1.setDate(LocalDate.now());
		Set<Visit> visits = new HashSet<Visit>();
		visits.add(visit1);
		tempPet.addVisit(visit1);
		assertSame(tempPet.getId(), visit1.getPetId());
	}

}
