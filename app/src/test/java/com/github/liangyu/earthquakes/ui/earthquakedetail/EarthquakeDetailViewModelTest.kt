package com.github.liangyu.earthquakes.ui.earthquakedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.liangyu.earthquakes.MainCoroutineRule
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.common.Result
import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.ui.model.Earthquake
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EarthquakeDetailViewModelTest {

    private lateinit var sut: EarthquakeDetailViewModel
    private var repository: EarthquakeRepository = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        sut = EarthquakeDetailViewModel(repository)
        coEvery { repository.getEarthquake(earthquakeEntity.eqid, any()) } returns successResponse
    }

    @Test
    fun `start earthquake with id, earthquake loaded`() {
        //Arrange
        //Act
        sut.start(earthquakeEntity.eqid)
        //Assert
        sut.earthquake.value!!.eqid shouldEqual earthquake.eqid
    }

    @Test
    fun `start earthquake makesure loading state is set`() {
        //Arrange
        mainCoroutineRule.pauseDispatcher()

        //Act
        sut.start(earthquakeEntity.eqid)

        //Assert
        sut.dataLoading.value shouldEqual true
        mainCoroutineRule.resumeDispatcher()
        sut.dataLoading.value shouldEqual false
    }

    @Test
    fun `start repository error earthquake value should be null`() {
        //Arrange
        coEvery { repository.getEarthquake(any(), any()) } returns errorResponse
        //Act
        sut.start(earthquakeEntity.eqid)
        //Assert
        sut.earthquake.value shouldEqual null
    }

    @Test
    fun `start repository error snackbar error`() {
        //Arrange
        coEvery { repository.getEarthquake(any(), any()) } returns errorResponse
        //Act
        sut.start(earthquakeEntity.eqid)
        //Assert
        sut.snackbarMessage.value!!.peekContent() shouldEqual R.string.loading_earthquake_error
    }

    //-------------------Helper----------------------------

    val earthquakeEntity = EarthquakeEntity("Title1", "date time", 20.9, 20.0, -15.1, 8.6, "US")
    val earthquake = Earthquake("Title1", "date time", 20.9, 20.0, -15.1, 8.6, "US", true)
    val successResponse = Result.Success(earthquakeEntity)
    val errorResponse = Result.Error(Exception("Unable to load earthquake"))
}
