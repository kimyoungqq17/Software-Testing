package org.springframework.unitTests.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.ui.ModelMap;
import org.springframework.util.SerializationUtils;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OwnerTests {

	Owner owner = new Owner();

	@Test
	@DisplayName("Testing owner creation")
	public void testOwnerConstructor() {
		assertNull(owner.getId());

		owner.setId(123);
		owner.setFirstName("Younggue");
		owner.setLastName("Kim");
		owner.setCity("Calgary");
		owner.setAddress("Somerset");
		owner.setTelephone("40312345678");

		Pet pet = new Pet();
		pet.setOwner(owner);
		pet.setName("Norange");
		pet.setId(1);
		pet.setBirthDate(LocalDate.now());
		HashSet<Pet> pets = new HashSet<Pet>();
		pets.add(pet);
		owner.setPetsInternal(pets);

		assertEquals("Younggue", owner.getFirstName());
		assertEquals("Somerset", owner.getAddress());
		assertEquals("Calgary", owner.getCity());
		assertEquals("Kim", owner.getLastName());
		assertEquals("40312345678", owner.getTelephone());
		assertSame(pets, owner.getPetsInternal());

		assertNotNull(owner.getId());
	}

	@Test
	@DisplayName("Testing getters and setters for address with a proper value")
	public void testSetAddressAndGetAddressSuccess() {
		owner.setAddress("Somerset");
		assertEquals("Somerset", owner.getAddress());
	}

	@Test
	@DisplayName("Testing getters and setters for address with non proper value")
	public void testSetAddressAndGetAddressFail() {
		owner.setAddress("Somerset");
		Assertions.assertNotEquals("Evergreen", owner.getAddress());
	}

	@Test
	@DisplayName("Testing getters and setters for city with a proper value")
	public void testSetCityAndGetCitySuccess() {
		owner.setCity("Calgary");
		assertEquals("Calgary", owner.getCity());
	}

	@Test
	@DisplayName("Testing getters and setters for city with non proper value")
	public void testSetCityAndGetCityFail() {
		owner.setCity("Calgary");
		Assertions.assertNotEquals("Edmonton", owner.getCity());
	}

	@Test
	@DisplayName("Testing getters and setters for telephone with a proper value")
	public void testSetTelephoneAndGetTelephoneSuccess() {
		owner.setTelephone("40312345678");
		assertEquals("40312345678", owner.getTelephone());
	}

	@Test
	@DisplayName("Testing getters and setters for telephone with non proper value")
	public void testSetTelephoneAndGetTelephoneFail() {
		owner.setTelephone("40312345678");
		Assertions.assertNotEquals("51412345678", owner.getTelephone());
	}

	@Test
	@DisplayName("Testing getters and setters for petInternals when there's no pet")
	public void testSetPetInternalsAndGetPetInternalsForNullPet() {
		assertNotNull((new Owner()).getPetsInternal());
		assertTrue((new Owner()).getPetsInternal().isEmpty());
	}

	@Test
	@DisplayName("Testing getters and setters for petInternals when there's a pet")
	public void testSetPetInternalsAndGetPetInternals() {
		Owner tempOwner = new Owner();
		Pet pet1 = new Pet();
		pet1.setOwner(tempOwner);
		pet1.setName("Gumdonge");
		pet1.setId(2);
		pet1.setBirthDate(LocalDate.now());
		Set<Pet> tempPets = new HashSet<Pet>();
		tempPets.add(pet1);
		assertTrue(tempPets.contains(pet1));
		tempOwner.setPetsInternal(tempPets);
		Set<Pet> setOfPets = tempOwner.getPetsInternal();
		assertSame(setOfPets, tempPets);
	}

	@Test
	@DisplayName("Testing a function addPet")
	public void testAddPetsForANewPet() {
		Pet pet = new Pet();
		pet.setId(null);
		Set<Pet> petsInternal = new HashSet<>();
		owner.setPetsInternal(petsInternal);
		owner.addPet(pet);
		assertEquals(1, petsInternal.size());
		assertEquals(owner, pet.getOwner());
	}

	@Test
	@DisplayName("Testing a function addPet")
	public void testAddPets() {
		Owner testingOwner = new Owner();
		Pet pet = new Pet();
		pet.setOwner(testingOwner);
		pet.setName("Norange");
		pet.setId(1);
		pet.setBirthDate(LocalDate.now());
		testingOwner.addPet(pet);
		Owner petOwner = pet.getOwner();
		assertNotNull(petOwner);
		assertSame(testingOwner, petOwner);
	}

	@Test
	@DisplayName("Testing a function getPets")
	public void testPets() {

		Pet pet = new Pet();
		pet.setOwner(owner);
		pet.setName("Norange");
		pet.setId(1);
		pet.setBirthDate(LocalDate.now());
		assertNotNull(pet);

		Pet pet1 = new Pet();
		pet1.setName("Gumdonge");
		pet1.setId(null);
		pet1.setBirthDate(LocalDate.now());
		assertNotNull(pet);

		HashSet<Pet> pets = new HashSet<Pet>();
		pets.add(pet);
		pets.add(pet1);
		assertEquals(2, pets.size());
		owner.setPetsInternal(pets);

		@Nested
		class testGetPets {

			@Test
			@DisplayName("Testing a function getPets()")
			public void testGetPetsWithOutParam() {
				List<Pet> testGetPets = owner.getPets();
				assertFalse(testGetPets.isEmpty());
				assertEquals(2, testGetPets.size());

				List<Pet> myPetsSorted = new ArrayList<>();
				myPetsSorted.add(pet1);
				myPetsSorted.add(pet);
				List<Pet> myPetsUnsorted = new ArrayList<>();
				myPetsUnsorted.add(pet);
				myPetsUnsorted.add(pet1);
				assertTrue(myPetsSorted.equals(testGetPets));
				assertFalse(myPetsUnsorted.equals((testGetPets)));

			}

			@Test
			@DisplayName("Testing a function getPets(String name)")
			public void testGetPetWithAParamName() {
				assertEquals(pet, owner.getPet("Norange"));
				assertNull(owner.getPet("not exist"));
			}

			@Test
			@DisplayName("Testing a function getPets(String name, false)")
			public void testGetPetsWithaParameter() {
				assertNull(owner.getPet("notExist", false));
				assertEquals(pet, owner.getPet("Norange", false));
			}

			@Test
			@DisplayName("Loop Coverage of Testing a function getPets(String name, boolean ignoreNew)")
			public void testGetPetsWithParametersWithALoopCoverage() {
				// zero times: do not enter the loop
				assertNull((new Owner()).getPet("not exist", true));
				assertNull((new Owner()).getPet("not exist", false));
				assertNull((new Owner()).getPet("Norange", true));
				assertNull((new Owner()).getPet("Norange", false));
				// once: do not repeat it
				Pet pet100 = new Pet();
				Owner owner2 = new Owner();
				pet100.setOwner(owner2);
				pet100.setName("tempPet");
				pet100.setId(100);
				HashSet<Pet> tempPet = new HashSet<Pet>();
				tempPet.add(pet100);
				owner2.setPetsInternal(tempPet);

				assertEquals(pet100, owner2.getPet("tempPet", true));
				assertEquals(pet100, owner2.getPet("tempPet", false));

				// twice: repeat it once or more times
				Pet pet200 = new Pet();
				Pet pet300 = new Pet();
				Owner owner3 = new Owner();
				pet200.setOwner(owner3);
				pet300.setOwner(owner3);
				pet200.setName("tempPet1");
				pet200.setId(200);
				pet300.setName("tempPet2");
				pet300.setId(300);
				HashSet<Pet> tempPet2 = new HashSet<Pet>();
				tempPet2.add(pet200);
				tempPet2.add(pet300);
				owner3.setPetsInternal(tempPet2);

				assertEquals(pet200, owner3.getPet("tempPet1", true));
				assertEquals(pet300, owner3.getPet("tempPet2", true));

				assertEquals(pet200, owner3.getPet("tempPet1", false));
				assertEquals(pet300, owner3.getPet("tempPet2", false));

			}

			// f f
			// f t
			@Test
			@DisplayName("Testing a function getPets(String name, boolean ignoreNew) when pet doesn't exist")
			public void testGetPetsWithParametersWhenNameNotExists() {
				assertNull(owner.getPet("notExist", false));
				assertNull(owner.getPet("notExist", true));
			}

			// Gumdonge => true when isNew()
			// Norange => false when isNew()
			// t t
			// t f
			// f f
			// f t
			@Test
			@DisplayName("Testing a function getPets(String name, boolean ignoreNew) when pet exist")
			public void testGetPetsWithParametersWhenPetExists() {
				assertEquals(pet, owner.getPet("Norange", true));
				assertEquals(pet, owner.getPet("Norange", false));
				assertEquals(pet1, owner.getPet("Gumdonge", false));
				assertNull(owner.getPet("Gumdonge", true));
			}

		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "1", "Kim", "Younggue", "Somerset", "Calgary", "4031234567" })
	@DisplayName("Testing toString method")
	public void testToString(String input) {
		owner.setId(1);
		owner.setLastName("Kim");
		owner.setFirstName("Younggue");
		owner.setAddress("Somerset");
		owner.setCity("Calgary");
		owner.setTelephone("4031234567");
		assertThat(owner.toString()).contains(input);
	}

}
