package org.springframework.IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Owner;

@ExtendWith(MockitoExtension.class)
class OwnerPetDependencyTests {

	@Mock
	private Pet pet;

	private Owner owner;

	@BeforeEach
	void setup() {
		this.owner = new Owner();
	}

	@Test
	void testAddPet() {
		when(this.pet.isNew()).thenReturn(true);
		this.owner.addPet(this.pet);
		List<Pet> pets = this.owner.getPets();
		assertTrue(pets.size() == 1);
	}

	@Test
	void testGetPet() {
		when(this.pet.isNew()).thenReturn(true);
		when(this.pet.getName()).thenReturn("Garfield");
		this.owner.addPet(this.pet);
		Pet myPet = this.owner.getPet("Garfield");
		assertEquals(myPet, this.pet);
	}

	@Test
	void testSetPetsInternal() {
		Set<Pet> pets = new HashSet<>();
		pets.add(this.pet);
		this.owner.setPetsInternal(pets);
		Set<Pet> myPets = this.owner.getPetsInternal();
		assertEquals(myPets, pets);
	}

}
