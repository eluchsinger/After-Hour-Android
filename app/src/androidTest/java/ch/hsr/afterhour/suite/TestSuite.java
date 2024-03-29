package ch.hsr.afterhour.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ch.hsr.afterhour.suite.tests.EventListTest;
import ch.hsr.afterhour.suite.tests.LoginTest;
import ch.hsr.afterhour.suite.tests.LogoutTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({EventListTest.class, LoginTest.class, LogoutTest.class})
public class TestSuite {
}
