package ch.hsr.afterhour.suite.tests;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.hsr.afterhour.GlobalSettings;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.LoginActivity;
import ch.hsr.afterhour.helperclasses.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Marcel on 04.04.17.
 */

@RunWith(AndroidJUnit4.class)
public class EventListTest {

    private GlobalSettings settings = new GlobalSettings();

    @Rule
    public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setup() {
        loginAsUser();
    }

    private void loginAsUser() {
        onView(withId(R.id.login_email_edittext)).perform(typeText(settings.getUserEmail()));
        onView(withId(R.id.login_password_edittext)).perform(typeText(settings.getPassword()));
        onView(withId(R.id.login_sign_in_button)).perform(click());
    }

    @Test
    public void ChangeToEvents_ShowsEventlist() {
        onView(withId(R.id.user_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.user_nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_item_user_login));
        onView(withId(R.id.eventlist)).check(matches(isDisplayed()));
    }

    @Test
    public void CheckIfAtLeastOneEventDownloaded() {
        onView(withId(R.id.user_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.user_nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_item_user_login));

        // Check item at position 1 has "Some content"
        onView(withRecyclerView(R.id.eventlist).atPosition(1))
                .check(matches(isDisplayed()));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

}
