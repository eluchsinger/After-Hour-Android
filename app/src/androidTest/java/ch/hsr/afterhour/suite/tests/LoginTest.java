package ch.hsr.afterhour.suite.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.hsr.afterhour.GlobalSettings;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    private GlobalSettings settings = new GlobalSettings();

    @Rule
    public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void UserLogin_ShowsProfile() {
        onView(withId(R.id.login_email_edittext)).perform(typeText(settings.getUserEmail()));
        onView(withId(R.id.login_password_edittext)).perform(typeText(settings.getPassword()));
        onView(withId(R.id.login_sign_in_button)).perform(click());
        onView(withId(R.id.profile_image_container)).check(matches(isDisplayed()));
    }

    @Test
    public void EmployeeLogin_ShowsScanner() {
        onView(withId(R.id.login_email_edittext)).perform(typeText(settings.getEmployeeEmail()));
        onView(withId(R.id.login_password_edittext)).perform(typeText(settings.getPassword()));
        onView(withId(R.id.login_sign_in_button)).perform(click());
        onView(withId(R.id.scanner_camera_view)).check(matches(isDisplayed()));
    }

    @Test
    public void WrongUsername_StaysInLoginActivityAndShowsSnackbar() {
        onView(withId(R.id.login_email_edittext)).perform(typeText("blabla@hsr.ch"));
        onView(withId(R.id.login_password_edittext)).perform(typeText(settings.getPassword()));
        onView(withId(R.id.login_sign_in_button)).perform(click());
        onView(allOf(withId(
                android.support.design.R.id.snackbar_text),
                withText(R.string.wrong_login_credentials)
        )).check(matches(isDisplayed()));
    }

    @Test
    public void WrongPassword_StaysInLoginActivityAndShowsSnackbar() {
        onView(withId(R.id.login_email_edittext)).perform(typeText(settings.getUserEmail()));
        onView(withId(R.id.login_password_edittext)).perform(typeText("123456789"));
        onView(withId(R.id.login_sign_in_button)).perform(click());
        onView(allOf(withId(
                android.support.design.R.id.snackbar_text),
                withText(R.string.wrong_login_credentials)
        )).check(matches(isDisplayed()));
    }

    @Test
    public void NoCredentials_SetsUsernameError() {
        onView(withId(R.id.login_email_edittext))
                .check(matches(hasFocus()));
    }
}
