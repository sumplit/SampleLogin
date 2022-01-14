package id.test.tada

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.test.tada.mvvm.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainInstrumentedTest {

    @get:Rule
    val mMainActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java
    )

    @Test
    fun launchMainActivity() {
        SystemClock.sleep(3000)
        onView(withId(R.id.toolbar)).perform(click())
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        SystemClock.sleep(1000)
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile))
        SystemClock.sleep(1000)
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        SystemClock.sleep(1000)
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_home))
        SystemClock.sleep(4000)
        onView(withId(R.id.rvListNews)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )

    }

    @Test
    fun testLogoutMainActivity() {
        onView(withId(R.id.toolbar)).perform(click())
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        SystemClock.sleep(1000)
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile))
        SystemClock.sleep(1000)
        onView(withId(R.id.btnLogout)).perform(click())
        SystemClock.sleep(1000)
    }
}