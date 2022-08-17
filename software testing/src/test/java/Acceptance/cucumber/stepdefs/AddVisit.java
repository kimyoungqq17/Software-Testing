package Acceptance.cucumber.stepdefs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class AddVisit {

	@LocalServerPort
	public int port;

	// to invoke test end points
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private VisitRepository visitRepository;

	HttpHeaders headers = new HttpHeaders();

	private List<Owner> existingOwners;

	private Pet selectedPet;

	private List<Visit> expectedVisits;

	private List<Visit> actualVisits;

	private Visit toAdd;

	private String pathUsed;

	private ResponseEntity<String> addVisitResponse;

	@Before
	public void setup() {
		actualVisits = new ArrayList<>();
		expectedVisits = new ArrayList<>();
	}

	@Given("the following information about a new visit")
	public void the_following_information_about_a_new_visit(final Visit visit) {
		toAdd = visit;
		int PetId = visit.getPetId();
		selectedPet = petRepository.findById(visit.getPetId());
		List<Visit> listOfVisits = visitRepository.findByPetId(selectedPet.getId());
		if (listOfVisits.size() > 0) {
			expectedVisits.addAll(listOfVisits);
		}

	}

	@And("the new visit has the date field specified as follows")
	public void the_new_visit_has_the_date_field_specified_as_follows(DataTable dataTable) {
		List<String> rows = dataTable.asList(String.class);
		String input = rows.get(0);
		boolean shouldAdd = true;
		// if not blank
		if (!input.equals("[blank]")) {
			// if valid format
			if (isDateValidFormat(input)) {
				shouldAdd = toAdd.setStringDate(input);
			}
			// if not blank, but invalid format
			else {
				shouldAdd = toAdd.setStringDate("");
			}
		}

		if (toAdd.getDescription() == null) {
			shouldAdd = false;
		}
		// if its blank do nothing. already has default value
		if (shouldAdd) {
			expectedVisits.add(toAdd);
		}

	}

	@When("the user submits the add visit form")
	public void the_user_submits_the_add_visit_form() throws JsonMappingException, JsonProcessingException {
		String url = "/owners/{ownerId}/pets/{petId}/visits/new";
		String ownerId = Integer.toString(selectedPet.getOwner().getId());
		String petId = Integer.toString(selectedPet.getId());
		// URI (URL) parameters
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("ownerId", ownerId);
		urlParams.put("petId", petId);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		pathUsed = builder.buildAndExpand(urlParams).toUri().toString();
		addVisitResponse = testRestTemplate.postForEntity(builder.buildAndExpand(urlParams).toUri(), toAdd,
				String.class);
		Pet p = new Pet();
	}

	@Then("the visit should be added to the database")
	public void the_visit_should_be_added_to_the_database() {
		int id = selectedPet.getId();
		List<Visit> returnedVisits = visitRepository.findByPetId(id);
		if (returnedVisits != null) {
			actualVisits.addAll(returnedVisits);
		}
		assertEquals(expectedVisits.size(), actualVisits.size());
		assertEquals(expectedVisits.get(expectedVisits.size() - 1).getId(),
				actualVisits.get(actualVisits.size() - 1).getId());

	}


	@Then("the visit should not be added to the database")
	public void the_visit_should_not_be_added_to_the_database() {
		int id = selectedPet.getId();
		List<Visit> returnedVisits = visitRepository.findByPetId(id);
		if (returnedVisits != null) {
			actualVisits.addAll(returnedVisits);
		}
		int expectedSize = expectedVisits.size();
		int actualSize = actualVisits.size();
		if (expectedSize == 0 || actualSize == 0) {
			assertEquals(expectedSize, actualSize);
		}
		else {
			assertEquals(expectedSize, actualSize);
			assertEquals(expectedVisits.get(expectedVisits.size() - 1).getId(),
					actualVisits.get(actualVisits.size() - 1).getId());
		}
	}

	@And("the system will redisplay the form page")
	public void the_system_will_redisplay_the_form_page() {
		Document doc = Jsoup.parse(addVisitResponse.getBody());
		Element form = doc.selectFirst("div.form-group");
		Elements inputs = form.select("div.form-group");
		List<String> actualLabelText = new ArrayList<>();
		actualLabelText.add(doc.selectFirst("button.btn").text());
		for (Element e : inputs) {
			actualLabelText.add(e.selectFirst("label").text());
		}

		assertTrue(actualLabelText.contains("Add Visit"));
		assertTrue(actualLabelText.contains("Date"));
		assertTrue(actualLabelText.contains("Description"));

	}

	@And("an error message under the description field will be displayed.")
	public void an_error_message_under_the_description_field_will_be_displayed() {
		Document doc = Jsoup.parse(addVisitResponse.getBody());
		Element form = doc.selectFirst("div.has-error");
		Element errorMessage = form.selectFirst("span.help-inline");
		assertEquals("must not be empty", errorMessage.text());
	}

	@After()
	public void tearDown() {
		expectedVisits.clear();
		actualVisits.clear();
	}

	// helper
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private boolean isDateValidFormat(String date) {
		Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		return p.matcher(date).matches();
	}

}
