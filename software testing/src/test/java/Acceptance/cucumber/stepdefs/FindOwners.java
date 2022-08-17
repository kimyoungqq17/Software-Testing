package Acceptance.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.test.annotation.DirtiesContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FindOwners {
	
	//to invoke test end points
		@Autowired
		private TestRestTemplate testRestTemplate; 
		
		@Autowired
		private OwnerRepository ownerRepo; 
		
		private List<Owner> expectedOwners;
		private List<Owner> actualOwners;
		private List<Owner> tmpList;
		private String allOwners;
		private String restOfOwners;
		private String error = "";
		private ResponseEntity<String> response ;
		
		@Before
		public void setUp() {
			expectedOwners = new ArrayList<>();
			actualOwners = new ArrayList<>();
			tmpList = new ArrayList<>();
			allOwners = "";
			restOfOwners = "";
		}

	@Given("the following owners exist in the system:")
	public void the_following_owners_exist_in_the_system(final List<Owner> ownerInfo) throws Exception {

		expectedOwners.addAll(ownerInfo);

		for (Owner owner : ownerInfo) {
			ownerRepo.save(owner);
		}

	}

	@When("User searches an owners with specified {string}")
	public void user_searches_an_owners_with_specified(String string) throws Exception {
		if (string.equals(" ")) {
			response = testRestTemplate.getForEntity("/owners", String.class);
		}
		else {
			response =  testRestTemplate.getForEntity("/owners?lastName="+string, String.class);
		}
		Collection<Owner> foundOwner = ownerRepo.findByLastName(string);
		if (!foundOwner.isEmpty()) {
			System.out.println("owner is found");
			String body = response.getBody();
			allOwners = body.substring(body.indexOf("<h2>Owners</h2>"));
			restOfOwners = ""+ htmlToList(allOwners, true);
		}
		else {
			error = "has not been found";
		}
	}

	@Then("the following owner information shall be displayed:")
	public void the_following_owner_information_shall_be_displayed(final List<Owner> ownerInfo) throws Exception {
		String toPass = allOwners.substring(allOwners.indexOf(restOfOwners));
		htmlToList(toPass, false);
		for(int i=0; i<actualOwners.size();i++) {
			assertEquals(actualOwners.get(i).getFirstName(), tmpList.get(i).getFirstName());
			assertEquals(actualOwners.get(i).getLastName(), tmpList.get(i).getLastName());
			assertEquals(actualOwners.get(i).getAddress(), tmpList.get(i).getAddress());
			assertEquals(actualOwners.get(i).getCity(), tmpList.get(i).getCity());
			assertEquals(actualOwners.get(i).getTelephone(), tmpList.get(i).getTelephone());
		}
	}

	@Test
	@Then("the following owners shall be displayed in the list:")
	public void the_following_owners_shall_be_displayed_in_the_list(final List<Owner> ownerInfo) throws Exception {
		String toPass = allOwners.substring(allOwners.indexOf(restOfOwners));
		htmlToList(toPass, false);
		for(int i=0; i<actualOwners.size();i++) {
			assertEquals(actualOwners.get(i).getFirstName(), tmpList.get(i).getFirstName());
			assertEquals(actualOwners.get(i).getLastName(), tmpList.get(i).getLastName());
			assertEquals(actualOwners.get(i).getAddress(), tmpList.get(i).getAddress());
			assertEquals(actualOwners.get(i).getCity(), tmpList.get(i).getCity());
			assertEquals(actualOwners.get(i).getTelephone(), tmpList.get(i).getTelephone());
		}
	}

	@Test
	@Then("the system shall raise the error {string}")
	public void the_system_shall_raise_the_error(String string) throws Exception {
		assertEquals ("has not been found", error);
		
	}
	
	private String htmlToList (String html, boolean all) {
		String[] toPass = {html, ""};
		if (all) toPass[1] = "all";
		String[] result = htmlToOwner(toPass);
		while (true) {
			if (result[1].equals("stop")) break;
			result = htmlToOwner(result);
			if (result[1].equals("stop")) break;
		}
		return result[0];
	}
	
	private String[] htmlToOwner (String[] list) {
		Owner nOwner = new Owner();
		String tmpFirst;
		if(list[1].equals("all")) {
			String tmp = list[0].substring(list[0].indexOf("<a href=\"/owners/"));
			tmpFirst = tmp.substring(tmp.indexOf(">")+1);
		}
		else {
			tmpFirst = list[0];
		}
		String firstName = tmpFirst.substring(0, tmpFirst.indexOf(" "));
		if (nameExists (actualOwners, firstName) && list[1].equals("all")) {
			String[] toReturn = {tmpFirst, "stop"};
			return toReturn;
		}
		nOwner.setFirstName(firstName);
		String tmpLast = tmpFirst.substring(tmpFirst.indexOf(" ")+1);
		nOwner.setLastName(tmpLast.substring(0, tmpLast.indexOf("</")));
		String tmpAddress = tmpLast.substring(tmpLast.indexOf("<td>"));
		nOwner.setAddress(tmpAddress.substring(tmpAddress.indexOf('>')+1, tmpAddress.indexOf("</")));
		String tmpCity = tmpAddress.substring(tmpAddress.indexOf("</td>"));
		String tmpCity2 = tmpCity.substring(tmpCity.indexOf("<td>"));
		nOwner.setCity(tmpCity2.substring(tmpCity2.indexOf('>')+1, tmpCity2.indexOf("</")));
		String tmpTelephone = tmpCity2.substring(tmpCity2.indexOf("</td>"));
		String tmpTelephone2 = tmpTelephone.substring(tmpTelephone.indexOf("<td>"));
		nOwner.setTelephone(tmpTelephone2.substring(tmpTelephone2.indexOf('>')+1, tmpTelephone2.indexOf("</")));
		
		String[] toReturn = {tmpTelephone2, list[1]};
		if(list[1].equals("all")) actualOwners.add(nOwner);
		else {
			tmpList.add(nOwner);
			if(tmpList.size() == actualOwners.size()) toReturn[1] = "stop";
		}
		return toReturn;
	}
	
	private boolean nameExists (List<Owner> list, String s) {
		for (int i = 0; i<list.size(); i++) {
			if (list.get(i).getFirstName().equals(s)) {
				return true;
			}
		}
		return false;

	}

}