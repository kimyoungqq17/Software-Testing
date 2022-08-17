package Acceptance.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.PetClinicApplication;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = { PetClinicApplication.class, CucumberIT.class },
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(plugin = { "pretty" }, tags = "", features = "src/test/java/Acceptance/features")
public class CucumberIT {

}
