package itb.sdrank.web.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ RegistrationControllerTest.class, AHPSelectionControllerTest.class,
    RatingSelectionControllerTest.class })
public class AllWebTests {

}
