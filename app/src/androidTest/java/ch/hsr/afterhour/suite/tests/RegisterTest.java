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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {

        private GlobalSettings settings = new GlobalSettings();

        @Rule
        public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

        @Test
        public void UserLogin_ShowsProfile() {
            onView(withId(R.id.login_register_button)).perform(click());
            onView(withId(R.id.register_firstname)).perform(typeText("Me"));
            onView(withId(R.id.register_lastname)).perform(typeText("World"));
            onView(withId(R.id.register_birth_year)).perform(typeText("2017"));
            onView(withId(R.id.register_birth_month)).perform(typeText("02"));
            onView(withId(R.id.register_birth_day)).perform(typeText("02"));
            onView(withId(R.id.register_email)).perform(scrollTo(), typeText("me@world.com"));
            onView(withId(R.id.register_password)).perform(scrollTo(), typeText("1234"));
            onView(withId(R.id.register_repeat_password)).perform(scrollTo(), typeText("1234"));
            onView(withId(R.id.register_vorwahl)).perform(scrollTo(), typeText("+41"));
            onView(withId(R.id.register_mobilenumber)).perform(scrollTo(), typeText("791234567"));
            onView(withId(R.id.register_register_button)).perform(scrollTo(), click());

            onView(withId(R.id.profile_image_container)).check(matches(isDisplayed()));
        }
}
