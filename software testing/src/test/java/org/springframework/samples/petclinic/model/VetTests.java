/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.util.SerializationUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
class VetTests {

	Vet vet = new Vet();

	Person person = new Person();

	@Test
	@DisplayName("Testing vet creation")
	public void testVetConstructor() {
		// given code
		vet.setFirstName("Zaphod");
		vet.setLastName("Beeblebrox");
		vet.setId(123);
		Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
		assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
		assertThat(other.getLastName()).isEqualTo(vet.getLastName());
		assertThat(other.getId()).isEqualTo(vet.getId());

		Specialty specialty = new Specialty();
		specialty.setId(1);
		specialty.setName("fix ouchy doggos");

		Set<Specialty> specialities = new HashSet<>();
		specialities.add(specialty);
		assertThat(other.getSpecialtiesInternal()).isEqualTo(vet.getSpecialtiesInternal());
	}

	@Test
	@DisplayName("Testing getters and setters for specailitiesInternal")
	public void testGetSpecialitiesInternalAndSetSpecialitiesInteranl() {
		Specialty specialty = new Specialty();
		specialty.setId(1);
		specialty.setName("fix ouchy doggos");

		Set<Specialty> specialities = new HashSet<>();
		specialities.add(specialty);
		vet.setSpecialtiesInternal(specialities);
		Set<Specialty> specialities2 = vet.getSpecialtiesInternal();
		assertSame(specialities, specialities2);
	}

	@Test
	@DisplayName("Testing getters and setters for specialitiesInternal when there's no pet")
	public void testSetPetInternalsAndGetPetInternalsForNullPet() {
		Vet tempVet = new Vet();
		assertTrue(tempVet.getSpecialties().isEmpty());
		assertNotNull(tempVet.getSpecialtiesInternal());
	}

	@Test
	@DisplayName("Testing a function getSpecialities")
	public void testGetSpecialities() {
		Specialty specialty1 = new Specialty();
		specialty1.setId(1);
		specialty1.setName("fix ouchy doggos");

		Specialty specialty2 = new Specialty();
		specialty2.setId(2);
		specialty2.setName("fix chubby doggos");

		Set<Specialty> specialitiesSet = new HashSet<>();
		specialitiesSet.add(specialty1);
		specialitiesSet.add(specialty2);
		vet.setSpecialtiesInternal(specialitiesSet);

		List<Specialty> unsortedSpecialities = new ArrayList<>();
		unsortedSpecialities.add(specialty1);
		unsortedSpecialities.add(specialty2);
		List<Specialty> sortedSpecialities = new ArrayList<>();
		sortedSpecialities.add(specialty2);
		sortedSpecialities.add(specialty1);

		List<Specialty> calledSpecialities = vet.getSpecialties();
		assertTrue(sortedSpecialities.equals(calledSpecialities));
		assertFalse(unsortedSpecialities.equals(calledSpecialities));

		assertEquals(2, vet.getSpecialties().size());

	}

	@Test
	@DisplayName("testing a function getSpecialities when specialty is null")
	public void testGetSpecialtiesWhenEmpty() {
		// Specialty specialty = new Specialty();
		// specialty.setId(null);
		// Set<Specialty> specialties = new HashSet<>();
		// specialties.add(specialty);
		// vet.setSpecialtiesInternal(specialties);

		assertTrue(vet.getSpecialties().isEmpty());
		assertTrue(vet.getSpecialtiesInternal().isEmpty());
	}

	@Test
	@DisplayName("testing a function getNrOfSpecialities")
	public void testGetNrOfSpecialities() {
		Specialty specialty1 = new Specialty();
		Specialty specialty2 = new Specialty();
		Set<Specialty> specialties = new HashSet<>();
		specialties.add(specialty1);
		specialties.add(specialty2);
		vet.setSpecialtiesInternal(specialties);

		assertEquals(2, vet.getNrOfSpecialties());
	}

	// rewrite it
	@Test
	@DisplayName("testing a function addSpeciality")
	public void testAddSpeciality() {
		Specialty specialty1 = new Specialty();
		Specialty specialty2 = new Specialty();
		Set<Specialty> specialties = new HashSet<>();
		specialties.add(specialty1);
		specialties.add(specialty2);
		vet.setSpecialtiesInternal(specialties);

		assertEquals(2, vet.getNrOfSpecialties());
	}

}
