package org.springframework.IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;

@ExtendWith(MockitoExtension.class)
class PetVisitDependencyTests {

	@Mock
	private Visit visit1;

	@Mock
	private Visit visit2;

	private Pet pet;

	@BeforeEach
	void setup() {
		this.pet = new Pet();
	}

	@Test
	void addVisitTest() {
		this.pet.addVisit(this.visit1);
		List<Visit> visits = this.pet.getVisits();
		assertEquals(visits.get(0), this.visit1);
	}

	@Test
	void setVisitsInternalTest() {
		Set<Visit> visits = new LinkedHashSet<>();
		visits.add(this.visit1);
		visits.add(this.visit2);
		this.pet.setVisitsInternal(visits);
		Set<Visit> myVisits = this.pet.getVisitsInternal();
		assertTrue(myVisits.equals(visits));
	}

}
