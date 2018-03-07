package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.intent.Intents;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<>(LoginActivity.class, true,
                    true);


    @Test
    public void testLoginError() {
        onView(withId(R.id.txtEmail)).perform(typeText("sd44sdf"));
        onView(withId(R.id.txtContrasena)).perform(replaceText("78"));
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.txtContrasena)).check(
                matches(hasErrorText(activityRule.getActivity().getString(R.string.error_contrasena_test))));
    }

    @Test
    public void testLoginOK() {
        Intents.init();
        onView(withId(R.id.txtEmail)).perform(typeText("43292360N"));
        onView(withId(R.id.txtContrasena)).perform(replaceText("1234"));
        onView(withId(R.id.btnLogin)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }


}
