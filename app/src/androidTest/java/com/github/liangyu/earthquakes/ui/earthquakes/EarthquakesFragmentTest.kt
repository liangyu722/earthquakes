package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.github.liangyu.earthquakes.DaggerTestApplicationRule
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.ui.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class EarthquakesFragmentTest {

    private lateinit var repository: EarthquakeRepositoryTD

    @get:Rule
    val rule = DaggerTestApplicationRule()

    @Before
    fun setupDaggerComponent() {
        repository = rule.component.repository as EarthquakeRepositoryTD
    }

    @Test
    fun displayEarthquake_whenRepositoryHasData() {
        // Arrange
        repository.addEarthQuakeToList(eq1)

        // Act
        launchActivity()

        // Assert
        onView(withText("8.6")).check(matches(isDisplayed()))
    }

    @Test
    fun displayMajorEarthquake() {
        // Arrange
        repository.addEarthQuakeToList(eq1)

        // Act
        launchActivity()

        // Assert
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    @Test
    fun displayMinorEarthquake() {
        // Arrange
        repository.addEarthQuakeToList(eq2)

        // Act
        launchActivity()

        // Assert
        onView(withId(R.id.imageView)).check(matches(not(isDisplayed())))
        onView(withText("7.6")).check(matches(isDisplayed()))
    }

    @Test
    fun displayMultipleEarthquakes() {
        // Arrange
        repository.addEarthQuakeToList(eq1)
        repository.addEarthQuakeToList(eq2)
        repository.addEarthQuakeToList(eq3)

        // Act
        launchActivity()

        // Assert
        onView(withText("8.6")).check(matches(isDisplayed()))
        onView(withText("7.6")).check(matches(isDisplayed()))
        onView(withText("2.6")).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnEarthquake_navigateToEarthquakeDetailFragment() {
        // Arrange
        repository.addEarthQuakeToList(eq1)
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_graph)

        val scenario = launchFragmentInContainer<EarthquakesFragment>()
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // Act
        onView(withText("8.6")).perform(ViewActions.click())

        // Assert
        if (navController.currentDestination?.id != R.id.earthquakeDetailFragment) {
            throw  AssertionError(
                "Earthquake destination error"
            )
        }
    }

    //----------------------- Helper -------------------
    private fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.earthquakes_list) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }

    private val eq1 = EarthquakeEntity("eq1", "datetime 1", 10.0, -15.5, 10.2, 8.6, "us")
    private val eq2 = EarthquakeEntity("eq2", "datetime 2", 12.0, -11.5, 80.2, 7.6, "us")
    private val eq3 = EarthquakeEntity("eq3", "datetime 3", 13.0, -15.8, 90.2, 2.6, "us")
}