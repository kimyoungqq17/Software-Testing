package org.springframework.samples.petclinic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.persistence.OwnerRepository;

@DataJpaTest
class OwnerRepositoryIntegrationTests {

	@Autowired
	private OwnerRepository ownerRepo;

	@Test
	void testSaveOwnerfindById() throws Exception {
		Owner owner = new Owner();
		owner.setAddress("480 Rue Sainte-Catherine");
		owner.setCity("Montréal");
		owner.setFirstName("Ryan");
		owner.setId(1);
		owner.setLastName("Sowa");
		owner.setTelephone("6024832839");
		Pet pet = new Pet();
		pet.setName("Raisin");
		pet.setOwner(owner);
		pet.setId(5);
		owner.addPet(pet);
		ownerRepo.save(owner);
		assertEquals("Ryan", ownerRepo.findById(1).getFirstName());

	}

	@Test
	void testSaveOwnerfindByLastName() throws Exception {
		Owner owner = new Owner();
		owner.setAddress("480 Rue Sainte-Catherine");
		owner.setCity("Montréal");
		owner.setFirstName("Ryan");
		owner.setId(1);
		owner.setLastName("Sowa");
		owner.setTelephone("6024832839");
		Pet pet = new Pet();
		pet.setName("Raisin");
		pet.setOwner(owner);
		pet.setId(5);
		owner.addPet(pet);
		ownerRepo.save(owner);
		Collection<Owner> owners = ownerRepo.findByLastName("Sowa");
		assertEquals("Montréal", owners.iterator().next().getCity());

	}

}
