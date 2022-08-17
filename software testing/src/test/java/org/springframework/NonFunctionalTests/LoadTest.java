package org.springframework.NonFunctionalTests;

import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.runner.parallel.ZeroCodeLoadRunner;
import org.junit.runner.RunWith;

@LoadWith("load_config.properties")
// set testMethod to testModifyOwnerInDb, testAddPetToDb, or testModifyPetInDb to load
// test other methods
@TestMapping(testClass = TestDriver.class, testMethod = "testAddOwnerToDb")
@RunWith(ZeroCodeLoadRunner.class)
public class LoadTest {

}
