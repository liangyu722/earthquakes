package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.github.liangyu.earthquakes.DaggerTestApplicationRule
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.ui.MainActivity
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
        onView(withText("8.6"))
            .check(matches(isDisplayed()))
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
    private val newEq = EarthquakeEntity("new eq", "datetime 4", 10.0, -15.5, 10.2, 9.6, "us")
}