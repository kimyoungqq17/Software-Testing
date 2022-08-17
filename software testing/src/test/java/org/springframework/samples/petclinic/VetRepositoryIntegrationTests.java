package org.springframework.samples.petclinic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.persistence.VetRepository;

@DataJpaTest
class VetRepositoryIntegrationTests {

	@Autowired
	VetRepository vetRepo;

	@Test
	void testfindAll() throws Exception {
		vetRepo.findAll();
	}

}
