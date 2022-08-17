package org.springframework.NonFunctionalTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.NonFunctionalTests.runner.ZeroCodeSpringJUnit4Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.PetClinicApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(ZeroCodeSpringJUnit4Runner.class)
@SpringBootTest(classes = PetClinicApplication.class)
@AutoConfigureMockMvc
public class TestDriver {

	@Autowired
	private MockMvc mockMvc;

	private static Owner john;

	private static Owner bob;

	private static Pet henry;

	private static Pet william;

	@BeforeClass
	public static void setup() {
		john = new Owner();
		john.setFirstName("John");
		john.setLastName("Doe");
		john.setAddress("123 McGill Street");
		john.setCity("Montreal");
		john.setTelephone("1234567890");
		john.setId(1);
		henry = new Pet();
		henry.setName("Henry");
		henry.setOwner(john);
		henry.setId(1);
		bob = new Owner();
		bob.setFirstName("Bob");
		bob.setLastName("Matthews");
		bob.setAddress("321 Sherbrooke Street");
		bob.setCity("Montreal");
		bob.setTelephone("1245678901");
		bob.setId(2);
		william = new Pet();
		william.setName("William");
		william.setOwner(bob);
		william.setId(2);
	}

	@Test
	public void testAddOwnerToDb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/owners/new").content(asJsonString(john))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testModifyOwnerInDb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/edit").content(asJsonString(bob))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testAddPetToDb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/new").content(asJsonString(henry))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testModifyPetInDb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/owners/2/pets/1/edit").content(asJsonString(william))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
