package org.springframework.samples.petclinic;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.persistence.VisitRepository;

@DataJpaTest
class VisitRepositoryIntegrationTests {

	@Autowired
	private VisitRepository vetRepo;

	@Test
	void testsaveVisit() throws Exception {
		LocalDate date = LocalDate.of(2017, 1, 13);
		Pet myPet = new Pet();
		Visit visit = new Visit();
		myPet.setId(4);
		myPet.setName("Asterix");
		visit.setPetId(4);
		visit.setId(6);
		visit.setDate(date);
		visit.setDescription("Great visit");
		myPet.addVisit(visit);
		vetRepo.save(visit);
		List<Visit> visits = vetRepo.findByPetId(4);
		assertEquals("Great visit", visits.get(0).getDescription());
	}

}
