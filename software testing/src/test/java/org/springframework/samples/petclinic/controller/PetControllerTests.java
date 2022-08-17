package org.springframework.samples.petclinic.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Iterator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

/**
 * Test class for the {@link PetController}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	int ownerId;

	@BeforeEach
	public void defaultOwner() throws Exception {
		ownerId = getIdFromMvcResult(createOwner());
	}

	@Test
	void testInitCreationFormNonExistentOwner() throws Exception {
		// fails because no check is done to protect null value access
		mockMvc.perform(get("/owners/" + 6757657 + "/pets/new")).andExpect(status().is4xxClientError());
	}

	@Test
	void testInitCreationFormExistentOwner() throws Exception {
		mockMvc.perform(get("/owners/" + ownerId + "/pets/new")).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"))
				.andExpect(model().attribute("pet", Matchers.any(Pet.class)));
	}

	@Test
	void testProcessCreationFormInvalidBirthDate() throws Exception {
		mockMvc.perform(post("/owners/" + ownerId + "/pets/new").param("name", "cat").param("birthDate", "2010-30-29")
				.param("type", "dog")).andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdatePetForm"))
				.andExpect(model().attribute("pet", Matchers.isA(Pet.class)));
	}

	@Test
	void testProcessCreationFormValidBirthDate() throws Exception {
		mockMvc.perform(post("/owners/" + ownerId + "/pets/new").param("name", "cat").param("birthDate", "2010-10-29")
				.param("type", "dog")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testProcessCreationFormDuplicatePetName() throws Exception {
		mockMvc.perform(post("/owners/" + ownerId + "/pets/new").param("name", "cat").param("birthDate", "2010-10-29")
				.param("type", "dog")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"))
				.andDo(result -> mockMvc
						.perform(post("/owners/" + ownerId + "/pets/new").param("name", "cat")
								.param("birthDate", "2010-30-29").param("type", "dog"))
						.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdatePetForm"))
						.andExpect(model().attribute("pet", Matchers.isA(Pet.class))));
	}

	@Test
	void testInitUpdateFormNonExistentPet() throws Exception {
		int id = 234239;
		// fails because nothing protects insertion of null values in model
		mockMvc.perform(get("/owners/" + ownerId + "/pets/" + id + "/edit")).andExpect(status().is4xxClientError());
	}

	@Test
	void testInitUpdateFormExistentPet() throws Exception {
		mockMvc.perform(post("/owners/" + ownerId + "/pets/new").param("name", "cat").param("birthDate", "2010-10-29")
				.param("type", "dog")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
		int id = getPetId(ownerId);

		mockMvc.perform(get("/owners/" + ownerId + "/pets/" + id + "/edit")).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"))
				.andExpect(model().attribute("pet", Matchers.hasProperty("id", Matchers.is(id))));
	}

	@Test
	void testProcessUpdateFormInvalidBirthDate() throws Exception {
		int id = 9;
		mockMvc.perform(post("/owners/" + ownerId + "/pets/" + id + "/edit").param("name", "okoko").param("birthDate",
				"2015-99-12")).andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdatePetForm"))
				.andExpect(model().attribute("pet",
						Matchers.hasProperty("owner", Matchers.hasProperty("id", Matchers.equalTo(ownerId)))));
	}

	@Test
	void testProcessUpdateFormValidBirthDate() throws Exception {
		mockMvc.perform(post("/owners/" + ownerId + "/pets/" + 77 + "/edit").param("name", "okoko")
				.param("birthDate", "2016-11-12").param("type", "dog")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));

	}

	private MvcResult createOwner() throws Exception {
		return mockMvc
				.perform(post("/owners/new").param("firstName", "Doremi").param("lastName", "Test")
						.param("address", "100 McTavish").param("city", "Brooklyn").param("telephone", "123456789"))
				.andExpect(status().is3xxRedirection()).andReturn();
	}

	private Integer getIdFromMvcResult(MvcResult result) {
		String name = result.getModelAndView().getViewName();
		return Integer.parseInt(name.replace("redirect:/owners/", ""));
	}

	private int getPetId(int ownerId) throws Exception {
		MvcResult result = mockMvc.perform(get("/owners/" + ownerId)).andExpect(status().isOk())
				.andExpect(model().size(1)).andReturn();
		ModelMap map = result.getModelAndView().getModelMap();
		Iterator<String> ite = map.keySet().iterator();
		Owner owner = (Owner) map.get(ite.next());
		return owner.getPets().get(0).getId();
	}

}