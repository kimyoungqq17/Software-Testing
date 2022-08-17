package org.springframework.samples.petclinic.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

/**
 * Test class for the {@link VisitController}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VisitControllerTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Here we are initializing the form for a pet that exists
	 * @throws Exception
	 */
	@Test
	void testInitNewVisitForm() throws Exception {
		int petId = getPetId();
		mockMvc.perform(get("/owners/1/pets/" + petId + "/visits/new")).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	/**
	 * Here we are initializing the form for a pet that do not exists. It fails because no
	 * check is done to ensure that the given id corresponds to an existing pet.
	 * @throws Exception
	 */
	@Test
	void testInitNewVisitFormNonExistentPetId() throws Exception {
		mockMvc.perform(get("/owners/1/pets/9891/visits/new")).andExpect(status().is4xxClientError());
	}

	/**
	 * Here we are creating or updating the form for a pet that exists. We do not test for
	 * the other path as this is related to spring itself and not our controller
	 * @throws Exception
	 */
	@Test
	void testProcessNewVisitFormNullVisit() throws Exception {
		int petId = getPetId();
		mockMvc.perform(post("/owners/1/pets/" + petId + "/visits/new")).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	@Test
	void testProcessNewVisitFormInvalidVisit() throws Exception {
		int petId = getPetId();
		mockMvc.perform(
				post("/owners/1/pets/" + petId + "/visits/new").param("description", "").param("date", "2013-02-10"))
				.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	@Test
	void testProcessNewVisitFormValidVisit() throws Exception {
		int petId = getPetId();
		mockMvc.perform(post("/owners/1/pets/" + petId + "/visits/new").param("description", "A random visit")
				.param("date", "2013-11-10")).andExpect(status().is3xxRedirection())
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

	private int getPetId() throws Exception {
		int ownerId = getIdFromMvcResult(createOwner());

		mockMvc.perform(post("/owners/" + ownerId + "/pets/new").param("name", "cat").param("birthDate", "2010-10-29")
				.param("type", "dog")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));

		MvcResult result = mockMvc.perform(get("/owners/" + ownerId)).andExpect(status().isOk())
				.andExpect(model().size(1)).andReturn();
		ModelMap map = result.getModelAndView().getModelMap();
		Iterator<String> ite = map.keySet().iterator();
		Owner owner = (Owner) map.get(ite.next());
		return owner.getPets().get(0).getId();
	}

}
