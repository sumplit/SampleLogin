package id.test.tada


import android.app.Instrumentation
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import id.test.tada.mvvm.auth.login.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {

    val res = getInstrumentation().getTargetContext().getResources()

    @get:Rule
    val mMainActivityTestRule: ActivityTestRule<LoginActivity> = ActivityTestRule(
        LoginActivity::class.java
    )

    @Test
    fun ensureTextView() {
        onView(withId(R.id.tvLogin)).check(matches(withText(res.getString(R.string.login))))
//        onView(withId(R.id.tvRegister)).check(matches(withText(res.getString(R.string.dont_have_account))))
    }

    @Test
    fun testInvalidaLogin() {
        onView(withId(R.id.btnLogin)).perform(click())
        onView(withId(R.id.txtLayoutUsername)).check(matches(hasDescendant(withText(res.getString(R.string.warning_username_empty)))))
        onView(withId(R.id.txtLayoutPassword)).check(matches(hasDescendant(withText(res.getString(R.string.warning_password_empty)))))
    }

    @Test
    fun testValidLogin() {
        onView(withId(R.id.edtInputUsername)).perform(typeText("diki"))
        onView(withId(R.id.edtInputPassword)).perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(click())
        SystemClock.sleep(4000)
    }


}