package org.springframework.samples.petclinic.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Iterator;

import org.hamcrest.Matchers;
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
 * Test class for the {@link OwnerController}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OwnerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new")).andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"))
				.andExpect(model().attribute("owner", Matchers.any(Owner.class)));
	}

	@Test
	void testProcessCreationFormEmpty() throws Exception {
		mockMvc.perform(post("/owners/new")).andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessCreationFormInvalid() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "").param("lastName", "Test")
				.param("address", "100 McTavish").param("city", "Brooklyn").param("telephone", "123456789"))
				.andExpect(status().isOk()).andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessCreationFormValid() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "John").param("lastName", "Test")
				.param("address", "100 McTavish").param("city", "Brooklyn").param("telephone", "123456789"))
				.andExpect(status().is3xxRedirection()).andReturn();
		MvcResult result = mockMvc
				.perform(post("/owners/new").param("firstName", "John").param("lastName", "Test")
						.param("address", "100 McTavish").param("city", "Brooklyn").param("telephone", "123456789"))
				.andExpect(status().is3xxRedirection()).andReturn();

		assertThat(result.getModelAndView().getViewName(), containsString("redirect:/owners/"));
		assertDoesNotThrow(() -> {
			String id = result.getModelAndView().getViewName().replace("redirect:/owners/", "");
			Integer.parseInt(id);
		});
	}

	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(view().name("owners/findOwners"))
				.andExpect(model().attribute("owner", Matchers.any(Owner.class)));
	}

	@Test
	void testProcessFindFormEmptyLastName() throws Exception {
		mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
	}

	@Test
	void testProcessFindFormNoResult() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "No one is called like this")).andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"));
	}

	@Test
	void testProcessFindFormOneResult() throws Exception {
		Integer id = getIdFromMvcResult(createOwner());

		mockMvc.perform(get("/owners").param("lastName", "Test")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/" + id));
	}

	@Test
	void testProcessFindFormMultipleResult() throws Exception {
		createOwner();
		createOwner();

		mockMvc.perform(get("/owners").param("lastName", "Test")).andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList")).andExpect(model().attributeExists("selections"));
	}

	@Test
	void testInitUpdateOwnerFormNonExistentOwner() throws Exception {
		// fails because there is no safety against invalid ids.
		// we can argue that it is not a problem since no such requests are made
		int id = 1239;
		mockMvc.perform(get("/owners/" + id + "/edit")).andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testInitUpdateOwnerFormExistentOwner() throws Exception {
		Integer id = getIdFromMvcResult(createOwner());
		// cannot check the attribute because it uses a generated name
		mockMvc.perform(get("/owners/" + id + "/edit")).andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessUpdateOwnerFormInvalid() throws Exception {
		int id = 50;
		mockMvc.perform(post("/owners/" + id + "/edit")).andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessUpdateOwnerFormValid() throws Exception {
		Integer id = getIdFromMvcResult(createOwner());
		mockMvc.perform(post("/owners/" + id + "/edit").param("firstName", "Doremi").param("lastName", "Test")
				.param("address", "100 McTavish").param("city", "Brooklyn").param("telephone", "123456789"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testShowOwnerWithNonExistentOwner() throws Exception {
		// we show that the test fails because it does not validate the existence of the
		// owner with id
		int id = 534980;
		mockMvc.perform(get("/owners/" + id)).andExpect(status().is4xxClientError());
	}

	@Test
	void testShowOwnerWithExistentOwner() throws Exception {
		int id = getIdFromMvcResult(createOwner());
		MvcResult result = mockMvc.perform(get("/owners/" + id)).andExpect(status().isOk()).andExpect(model().size(1))
				.andReturn();
		ModelMap map = result.getModelAndView().getModelMap();
		Iterator<String> ite = map.keySet().iterator();
		assertTrue(ite.hasNext());
		assertTrue(map.get(ite.next()) instanceof Owner);
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

}
