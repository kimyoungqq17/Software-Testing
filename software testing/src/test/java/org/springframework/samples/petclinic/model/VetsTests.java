package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VetsTests {

	Vets vets = new Vets();

	@Test
	@DisplayName("Testing getVetList when vets is null")
	public void testGetVetListWhenNull() {
		// vets = null;
		assertNotNull(vets.getVetList());
		assertNotNull(new Vets().getVetList());
		assertTrue(new Vets().getVetList().isEmpty());
	}

	@Test
	@DisplayName("Testing getVetList when vets is null")
	public void testGetVetListWhenNotNull() {

		Vet vet1 = new Vet();
		Vet vet2 = new Vet();
		vets.addVet(vet1);
		vets.addVet(vet2);

		List<Vet> vetList = new ArrayList<>();
		vetList.add(vet1);
		vetList.add(vet2);
		assertEquals(vets.getVetList(), vetList);

	}

}
