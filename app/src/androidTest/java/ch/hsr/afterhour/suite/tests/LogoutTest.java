package ch.hsr.afterhour.suite.tests;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import ch.hsr.afterhour.GlobalSettings;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LogoutTest {

    private GlobalSettings settings = new GlobalSettings();

    @Rule
    public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void LogoutAsUser_ShowsLoginScreen() {
        onView(withId(R.id.login_email_edittext)).perform(typeText(settings.getUserEmail()));
        onView(withId(R.id.login_password_edittext)).perform(typeText(settings.getPassword()));
        onView(withId(R.id.login_sign_in_button)).perform(click());
        onView(withId(R.id.user_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.user_nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_item_logout));
        onView(withId(R.id.login_email_edittext)).check(matches(isDisplayed()));
    }

    @Test
    public void LogoutAsEmployee_ShowsLoginScreen() {
        onView(withId(R.id.login_email_edittext)).perform(typeText(settings.getEmployeeEmail()));
        onView(withId(R.id.login_password_edittext)).perform(typeText(settings.getPassword()));
        onView(withId(R.id.login_sign_in_button)).perform(click());
        onView(withId(R.id.employee_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.employee_nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.employee_nav_logout));
        onView(withId(R.id.login_email_edittext)).check(matches(isDisplayed()));
    }
}
