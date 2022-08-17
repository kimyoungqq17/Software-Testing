package org.springframework.samples.petclinic.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for the {@link CrashController}
 */
@WebMvcTest(CrashController.class)
public class CrashControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testTriggerException() throws Exception {
		boolean exceptionCaught = false;
		String msg = null;
		String expected = "Expected: controller used to showcase what happens when an exception is thrown";
		try {
			mockMvc.perform(get("/oups")).andExpect(status().is4xxClientError()).andReturn();
		}
		catch (Exception e) {
			exceptionCaught = true;
			msg = e.getMessage();
		}
		assertTrue(exceptionCaught);
		assertTrue(msg.contains(expected));
	}

}
