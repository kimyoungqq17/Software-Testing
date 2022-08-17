package Acceptance.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.persistence.OwnerRepository;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;

public class AddUser {

	// to invoke test end points
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private OwnerRepository ownerRepository;

	private List<Owner> expectedOwners;

	private List<Owner> actualOwners;

	private Owner ownerToBeAdded;

	private ResponseEntity<String> addOwnerReponse;

	@Before
	public void setUp() {
		expectedOwners = new ArrayList<>();
		actualOwners = new ArrayList<>();

		ResponseEntity<String> response = testRestTemplate.getForEntity("/owners", String.class);
		Document doc = Jsoup.parse(response.getBody());
		Element table = doc.selectFirst("table");
		Elements row = doc.selectFirst("table").select("tr");

		for (int i = 1; i < row.size(); i++) { // first row is the col names so skip it.
			Element aRow = row.get(i);
			Elements cols = aRow.select("td");
			Owner rowOwner = new Owner();
			rowOwner.setFirstName(cols.get(0).text());
			rowOwner.setAddress(cols.get(1).text());
			rowOwner.setCity(cols.get(2).text());
			rowOwner.setTelephone(cols.get(3).text());
			rowOwner.setId(i);
			expectedOwners.add(rowOwner);
			actualOwners.add(rowOwner);
		}
	}

	@Given("the following information for an owner")
	public void the_following_information_for_an_owner(final Owner ownerInformation) {
		ownerToBeAdded = ownerInformation;
		expectedOwners.add(ownerInformation);
	};

	@When("the user requests to add a new owner")
	public void the_user_tequests_to_add_a_new_owner() {
		// ownerRepository.save(ownerToBeAdded);
		addOwnerReponse = testRestTemplate.postForEntity("/owners/new", ownerToBeAdded, String.class);
	}

	@Then("the new owner should be added")
	public void the_new_owner_should_be_added() {
		Owner returnedOwner = ownerRepository.findById(ownerToBeAdded.getId());
		actualOwners.add(returnedOwner);
		assertEquals(expectedOwners.get(expectedOwners.size() - 1), actualOwners.get(actualOwners.size() - 1));
	}

	@Then("the new owner should not be added")
	public void the_new_owner_should_not_be_added() {
		Owner returnedOwner = ownerRepository.findById(ownerToBeAdded.getId());
		assertNull(returnedOwner);
	}

	@And("an error message should be displayed under the first name field")
	public void an_error_message_should_be_displayed_under_the_first_name_field() {
		Document doc = Jsoup.parse(addOwnerReponse.getBody());
		Elements firstNameInput = doc.select("div.has-error");
		for (Element el : firstNameInput) {
			if (el.select("input#firstName") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

	@And("an error message should be displayed under the last name field")
	public void an_error_message_should_be_displayed_under_the_last_name_field() {
		Document doc = Jsoup.parse(addOwnerReponse.getBody());
		Elements lastNameInput = doc.select("div.has-error");
		for (Element el : lastNameInput) {
			if (el.select("input#lastName") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

	@And("an error message should be displayed under the city field")
	public void an_error_message_should_be_displayed_under_the_city_field() {
		Document doc = Jsoup.parse(addOwnerReponse.getBody());
		Elements cityInput = doc.select("div.has-error");
		for (Element el : cityInput) {
			if (el.select("input#city") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

	@And("^an error message should be displayed under the address field$")
	public void an_error_message_should_be_displayed_under_the_address_field() {
		Document doc = Jsoup.parse(addOwnerReponse.getBody());
		Elements addressInput = doc.select("div.has-error");
		for (Element el : addressInput) {
			if (el.select("input#address") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

	@And("an error message should be displayed under the telephone field")
	public void an_error_message_should_be_displayed_under_the_telephone_name_field() {
		Document doc = Jsoup.parse(addOwnerReponse.getBody());
		Elements addressInput = doc.select("div.has-error");
		for (Element el : addressInput) {
			if (el.select("input#address") != null) {
				String telephone = el.select("span.help-inline").text();
				assertEquals("must not be empty",telephone);
				return;
			}
		}
		fail();
	}

}
