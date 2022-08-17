package org.springframework.IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;

@ExtendWith(MockitoExtension.class)
class VetVetsDependencyTests {

	@Mock
	private Vet vet1;

	@Mock
	private Vet vet2;

	private Vets vets;

	@BeforeEach
	void setup() {
		this.vets = new Vets();
	}

	@Test
	void testAddSpeciality() {
		this.vets.addVet(this.vet1);
		this.vets.addVet(this.vet2);
		List<Vet> vetList = this.vets.getVetList();
		assertEquals(this.vet1, vetList.get(0));
		assertEquals(this.vet2, vetList.get(1));
	}

}
