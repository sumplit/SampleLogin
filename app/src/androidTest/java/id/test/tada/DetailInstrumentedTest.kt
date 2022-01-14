package id.test.tada

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.test.tada.mvvm.main.listnews.detail.DetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailInstrumentedTest {

    @get:Rule
    val mDetailActivityTestRule: ActivityTestRule<DetailActivity> = ActivityTestRule(
        DetailActivity::class.java
    )

    @Test
    fun allDisplay() {

        onView(withId(R.id.iv_news)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

    }

    @Test
    fun testOnBackAction() {
        onView(withId(R.id.ivBack)).perform(click())
    }
}