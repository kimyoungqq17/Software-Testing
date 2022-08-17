package org.springframework.IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;

@ExtendWith(MockitoExtension.class)
class VetSpecialtyDependencyTests {

	@Mock
	private Specialty specialty1;

	@Mock
	private Specialty specialty2;

	private Vet vet;

	@BeforeEach
	void setup() {
		this.vet = new Vet();
	}

	@Test
	void testAddSpeciality() {
		this.vet.addSpecialty(this.specialty1);
		List<Specialty> specialties = this.vet.getSpecialties();
		assertEquals(this.specialty1, specialties.get(0));
	}

	@Test
	void testgetNrOfSpecialties() {
		this.vet.addSpecialty(this.specialty1);
		this.vet.addSpecialty(this.specialty2);
		assertEquals(this.vet.getNrOfSpecialties(), 2);
	}

}
