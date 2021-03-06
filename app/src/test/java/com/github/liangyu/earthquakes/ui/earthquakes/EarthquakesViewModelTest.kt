package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.liangyu.earthquakes.MainCoroutineRule
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.common.Result
import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.ui.model.Earthquake
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EarthquakesViewModelTest {

    private lateinit var sut: EarthquakesViewModel
    private val repository: EarthquakeRepository = mockk()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        coEvery { repository.getEarthquakes(any()) } returns successRepositoryResponse
        sut = EarthquakesViewModel(repository)
    }

    @Test
    fun `loadEarthquakes correct value passed to repository`() {
        //Arrange
        //Act
        sut.loadEarthquakes(false)
        //Assert
        coVerify(exactly = 1) { repository.getEarthquakes(false) }
    }

    @Test
    fun `loadEarthquakes data loading is true when loading data`() {
        //Arrange
        mainCoroutineRule.pauseDispatcher()
        //Act
        sut.loadEarthquakes(true)
        //Assert
        sut.dataLoading.value shouldEqual true
        mainCoroutineRule.resumeDispatcher()
    }

    @Test
    fun `loadEarthquakes data loading is false when finish loading data`() {
        //Arrange
        //Act
        sut.loadEarthquakes(true)
        //Assert
        sut.dataLoading.value shouldEqual false
    }

    @Test
    fun `loadEarthquakes data is loaded`() {
        //Arrange
        //Act
        sut.loadEarthquakes(true)
        //Assert
        sut.items.value shouldEqual successResponse.data
    }

    @Test
    fun `loadEarthquakes error item list should be empty`() {
        //Arrange
        coEvery { repository.getEarthquakes(any()) } returns errorResponse
        //Act
        sut.loadEarthquakes(true)
        //Assert
        sut.items.value shouldEqual emptyList()
    }

    @Test
    fun `loadEarthquakes error should show snackbar error`() {
        //Arrange
        coEvery { repository.getEarthquakes(any()) } returns errorResponse
        //Act
        sut.loadEarthquakes(true)
        //Assert
        sut.snackbarMessage.value!!.peekContent() shouldEqual R.string.loading_earthquake_error
    }

    @Test
    fun `openEarthquake value should be set`() {
        //Arrange
        val eqid = "eq1"
        //Act
        sut.openEarthquake(eqid)
        //Assert
        sut.openEarthquakeEvent.value!!.peekContent() shouldEqual eqid
    }

    @Test
    fun `loadEarthquakes magnitude 8 or greater should be major`() {
        //Arrange
        //Act
        sut.loadEarthquakes(true)
        //Assert
        sut.items.value!![0].major shouldEqual true
    }

    @Test
    fun `loadEarthquakes magnitude less than 8 is not major`() {
        //Arrange
        //Act
        sut.loadEarthquakes(true)
        //Assert
        sut.items.value!![1].major shouldEqual false
        sut.items.value!![2].major shouldEqual false
    }

    //------------------------- Helper ----------------------------------------------------
    private val eqentity1 = EarthquakeEntity("eq1", "datetime 1", 10.0, -15.5, 10.2, 8.6, "us")
    private val eqentity2 = EarthquakeEntity("eq2", "datetime 2", 12.0, -11.5, 80.2, 7.6, "us")
    private val eqentity3 = EarthquakeEntity("eq3", "datetime 3", 13.0, -15.8, 90.2, 2.6, "us")

    private val eq1 = Earthquake("eq1", "datetime 1", 10.0, -15.5, 10.2, 8.6, "us", true)
    private val eq2 = Earthquake("eq2", "datetime 2", 12.0, -11.5, 80.2, 7.6, "us", false)
    private val eq3 = Earthquake("eq3", "datetime 3", 13.0, -15.8, 90.2, 2.6, "us", false)

    private val successRepositoryResponse = Result.Success(listOf(eqentity1, eqentity2, eqentity3))
    private val successResponse = Result.Success(listOf(eq1, eq2, eq3))
    private val errorResponse = Result.Error(Exception("error loading earthquake"))

}
