package org.springframework.IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;

@ExtendWith(MockitoExtension.class)
class PetPetTypeDependencyTests {

	private Pet pet;

	@Mock
	private PetType type;

	@BeforeEach
	void setup() {
		this.pet = new Pet();
	}

	@Test
	void testsetType() {
		this.pet.setType(this.type);
		assertEquals(type, this.pet.getType());
	}

}
