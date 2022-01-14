package id.test.tada

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import id.test.tada.mvvm.auth.register.RegisterActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterInstrumentedTest {

    val res = getInstrumentation().getTargetContext().getResources()

    @get:Rule
    val mMainActivityTestRule: ActivityTestRule<RegisterActivity> = ActivityTestRule(
        RegisterActivity::class.java
    )

    @Test
    fun ensureTextView() {
        onView(withId(R.id.tvTitleRegister)).check(matches(withText(res.getString(R.string.register))))
    }

    @Test
    fun testInvalidRegister() {
        onView(withId(R.id.btnRegister)).check(matches(not(isEnabled())))
        onView(withId(R.id.edtInputUsername)).perform(typeText("diki"))
        onView(withId(R.id.btnRegister)).check(matches(not(isEnabled())))
        onView(withId(R.id.edtInputPassword)).perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.btnRegister)).check(matches(not(isEnabled())))
        onView(withId(R.id.cbAgree)).perform(click())
        onView(withId(R.id.btnRegister)).check(matches(isEnabled()))
        SystemClock.sleep(1000)
    }

    @Test
    fun testValidRegister() {
        onView(withId(R.id.edtInputUsername)).perform(typeText("diki"))
        onView(withId(R.id.edtInputPassword)).perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.cbAgree)).perform(click())
        onView(withId(R.id.btnRegister)).check(matches(isEnabled()))
        onView(withId(R.id.btnRegister)).perform(click())
        SystemClock.sleep(1000)
    }

}